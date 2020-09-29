package com.github.rinacm.sayaka.common.message.factory

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.contextual.WrappedExecutor
import com.github.rinacm.sayaka.common.message.error.IrrelevantMessageException
import com.github.rinacm.sayaka.common.message.error.PipelineException
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.shared.CommandFactory
import com.github.rinacm.sayaka.common.shared.CommandFactory.toRawCommand
import com.github.rinacm.sayaka.common.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.data.content
import java.time.Duration
import kotlin.reflect.full.createInstance

open class DefaultDispatcherImpl : Dispatcher {
    companion object Key : Dispatcher.Key<DefaultDispatcherImpl> {
        override val match: String = "Default"
        override val description: String = "Bot的默认调度器, 将会发送异常信息并将调用栈记录到文件中"
        override val instance = DefaultDispatcherImpl()
    }

    // ========================================================================
    // message table to manage the request frequency
    // ========================================================================
    private val messageTable: MutableMap<User, Pair<Int, Long>> = mutableMapOf()

    // ========================================================================
    // blacklist of user's that sending commands too fast
    // ========================================================================
    private val blacklist: MutableSet<User> = mutableSetOf()

    override suspend fun MessageEvent.translateMessage(): Command {
        // extract the PlainText message in the messageEvent
        val plainTextMessage = message[PlainText]?.content ?: throws<IrrelevantMessageException>()
        // attempts to translate the plainTextMessage into raw command
        val raw = plainTextMessage.toRawCommand()
        val cmdClass = CommandFactory.lookup(raw.name)
        // if the command is marked as PermanentlyDisable or RespondingType does not comfort MessageEvent
        // invoke thresholding before the null checking of cmdClass to handle the cases which cannot located
        // the Command class
        if (cmdClass == null || !CommandFactory.checkAccess(cmdClass, this) || !thresholding())
            throws<IrrelevantMessageException>()
        val contextual = CommandFactory.getContextual(cmdClass)
        CommandFactory.validate(this, cmdClass, cmdClass.annotation())
        @Suppress("UNCHECKED_CAST")
        return (contextual.translator.createInstance() as WrappedExecutor<MessageEvent, Command>).executeWrapped(this)
    }

    override suspend fun <T : Command> MessageEvent.dispatchMessage(command: T) {
        @Suppress("UNCHECKED_CAST")
        val handler = CommandFactory.getContextual(command::class).handler.createInstance() as WrappedExecutor<T, Unit>
        handler.executeWrapped(command)
    }

    override suspend fun MessageEvent.dispatchError(exception: Exception) {
        when {
            exception is IrrelevantMessageException || exception is PipelineException && exception.e is IrrelevantMessageException -> return
            else -> {
                val path = BotContext.getCrashReportPath()
                @Suppress("DuplicatedCode")
                File.write(path.toAbsolutePath().toString(), buildString {
                    appendLine("在处理由${senderName}(${sender.id})发来的${message.content}消息时出现了异常")
                    if (exception is PipelineException) {
                        appendLine("异常捕获于: ${exception.errorStage.localizedName}(${exception.errorStage})阶段，日志如下:")
                    }
                    appendLineIndent("异常信息: ${exception.message}")
                    appendLineIndent("异常堆栈: ")
                    appendIndent(exception.stackTraceToString(), indentLevel = 2)
                })
                try {
                    subject.sendMessage(buildString {
                        appendLine("在处理由${senderName}(${sender.id})发来的${message.content}消息时出现了异常，异常信息已经写入${path.toAbsolutePath()}")
                        appendLine("异常信息: $exception")
                        appendLine("由于: ${exception.cause?.toString()}")
                    })
                } catch (e: Exception) { /* ignore */
                }
            }
        }
    }

    override suspend fun MessageEvent.thresholding(): Boolean {
        with(sender) {
            // if the sender is already in the blacklist
            if (sender in blacklist) return false
            // if sender is in the table
            if (this in messageTable.keys) {
                val (times, startTime) = messageTable[this]!!
                // increase the successfully match counts (received a message and successfully located the
                // corresponding Command class will be considered as a successfully match)
                val newTime = times + 1
                messageTable[this] = newTime to startTime
                // if the match counts is greater than 2 (means it has been successfully matched for 3 times)
                // and the total in milliseconds is smaller 4 means there're at least 3 successfully matches
                // in 4 seconds, and that's our frequency control threshold, any requests over this frequency
                // are treated at potential malicious behavior
                if (System.currentTimeMillis() - startTime <= 1000 * 4 && newTime >= 2) {
                    // remove the criminal from messageTable and add it to the blacklist
                    messageTable.remove(this)
                    blacklist.add(this).apply {
                        // start a new coroutine and release the criminal after 2 minutes
                        launch(Dispatchers.IO) {
                            delay(Duration.ofMinutes(20).toMillis())
                            blacklist.remove(this@with)
                            subject.sendMessage(buildMessageChain {
                                if (sender is Member) {
                                    add((sender as Member).at())
                                }
                                add("你已经被移出黑名单")
                            })
                        }
                    }
                    subject.sendMessage(buildMessageChain {
                        if (sender is Member) {
                            add((sender as Member).at())
                        }
                        add("你在四秒内发送了超过三条消息，已经被拉入黑名单，2分钟后将会解禁")
                    })
                    return false
                } else {
                    // if the match counts is greater than 2 but has normal frequency means it's a legal session
                    if (newTime >= 2)
                        messageTable.remove(this)
                    return true
                }
            } else {
                // add if the sender is not in the messageTable yet
                return if (this !in messageTable.keys) {
                    messageTable[this] = 0 to System.currentTimeMillis()
                    true
                } else false
            }
        }
    }
}