@file:Suppress("unused")

package com.github.rinacm.sayaka.common.message.factory

import com.github.rinacm.sayaka.common.message.error.IrrelevantMessageException
import com.github.rinacm.sayaka.common.message.error.PipelineException
import com.github.rinacm.sayaka.common.util.appendIndent
import com.github.rinacm.sayaka.common.util.appendLineIndent
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.content

class DebugDispatcherImpl : DefaultDispatcherImpl() {
    companion object Key : Dispatcher.Key<DebugDispatcherImpl> {
        override val match: String = "Debug"
        override val description: String = "调试用调度器: 所有异常堆栈信息将被显式的发送 (对于所有包括来自QQ群或私聊的响应均有效)"
        override val instance = DebugDispatcherImpl()
    }

    override suspend fun dispatchError(messageEvent: MessageEvent, exception: Exception) {
        when {
            exception is IrrelevantMessageException || exception is PipelineException && exception.e is IrrelevantMessageException -> return
            else -> {
                @Suppress("DuplicatedCode")
                messageEvent.subject.sendMessage(buildString {
                    appendLine("在处理由${messageEvent.senderName}(${messageEvent.sender.id})发来的${messageEvent.message.content}消息时出现了异常")
                    if (exception is PipelineException) {
                        appendLine("异常捕获于: ${exception.errorStage.localizedName}(${exception.errorStage})阶段，日志如下:")
                    }
                    appendLineIndent("异常信息: ${exception.message}")
                    appendLineIndent("异常堆栈: ")
                    appendIndent(exception.stackTraceToString(), indentLevel = 2)
                })
            }
        }
    }
}