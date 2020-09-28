package com.github.rinacm.sayaka.common.message.factory

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.contextual.WrappedExecutor
import com.github.rinacm.sayaka.common.message.error.IrrelevantMessageException
import com.github.rinacm.sayaka.common.message.error.PipelineException
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.shared.CommandFactory
import com.github.rinacm.sayaka.common.shared.CommandFactory.toRawCommand
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import kotlin.reflect.full.createInstance

object DefaultDispatcherImpl : Dispatcher {
    override suspend fun MessageEvent.translateMessage(): Command {
        // extract the PlainText message in the messageEvent
        val plainTextMessage = message[PlainText]?.content ?: throws<IrrelevantMessageException>()
        // attempts to translate the plainTextMessage into raw command
        val raw = plainTextMessage.toRawCommand()
        val cmdClass = CommandFactory.lookup(raw.name)
        // if the command is marked as PermanentlyDisable or RespondingType does not comfort MessageEvent
        if (!CommandFactory.checkAccess(cmdClass, this))
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
                    subject.sendMessage("在处理由${senderName}(${sender.id})发来的${message.content}消息时出现了异常，异常信息已经写入${path.toAbsolutePath()}")
                } catch (e: Exception) { /* ignore */
                }
            }
        }
    }
}