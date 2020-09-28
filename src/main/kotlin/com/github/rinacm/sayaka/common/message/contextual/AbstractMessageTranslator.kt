package com.github.rinacm.sayaka.common.message.contextual

import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.shared.CommandFactory.toRawCommand
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText

abstract class AbstractMessageTranslator<T : Command> : MessageTranslator<T> {
    abstract override fun translate(rawInput: MessageEvent): T

    protected fun defaultTrailingParameters(rawInput: MessageEvent): List<String> {
        return with((rawInput.message[1] as PlainText).content.toRawCommand().parameters) {
            when {
                isEmpty() -> emptyList()
                indexOf(' ') == -1 -> listOf(this)
                else -> split(' ')
            }
        }
    }

    protected fun defaultOnlyOneParameter(rawInput: MessageEvent): String? {
        return defaultTrailingParameters(rawInput).firstOrNull()
    }
}