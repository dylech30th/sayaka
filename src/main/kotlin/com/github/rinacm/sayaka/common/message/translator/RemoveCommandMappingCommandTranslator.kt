package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.RemoveCommandMappingCommand
import net.mamoe.mirai.message.MessageEvent

class RemoveCommandMappingCommandTranslator : AbstractMessageTranslator<RemoveCommandMappingCommand>() {
    override fun translate(rawInput: MessageEvent): RemoveCommandMappingCommand {
        return RemoveCommandMappingCommand(rawInput, defaultOnlyOneParameter(rawInput)!!)
    }
}