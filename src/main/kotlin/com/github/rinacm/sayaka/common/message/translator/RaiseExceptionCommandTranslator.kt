package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.RaiseExceptionCommand
import net.mamoe.mirai.message.MessageEvent

class RaiseExceptionCommandTranslator : AbstractMessageTranslator<RaiseExceptionCommand>() {
    override fun translate(rawInput: MessageEvent): RaiseExceptionCommand {
        return RaiseExceptionCommand(rawInput)
    }
}