package com.github.rinacm.sayaka.common.message.contextual

import com.github.rinacm.sayaka.common.message.error.TranslationException
import com.github.rinacm.sayaka.common.shared.Command
import net.mamoe.mirai.message.MessageEvent

interface MessageTranslator<T : Command> : WrappedExecutor<MessageEvent, T> {
    fun translate(rawInput: MessageEvent): T

    override suspend fun executeWrapped(parameter: MessageEvent): T {
        try {
            return translate(parameter)
        } catch (e: Exception) {
            throw TranslationException(e)
        }
    }
}