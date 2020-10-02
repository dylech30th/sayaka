package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.SetCommandMappingCommand
import net.mamoe.mirai.message.MessageEvent

class SetCommandMappingCommandTranslator : AbstractMessageTranslator<SetCommandMappingCommand>() {
    override fun translate(rawInput: MessageEvent): SetCommandMappingCommand {
        val p = defaultTrailingParameters(rawInput)
        return SetCommandMappingCommand(rawInput, p[0], p[1])
    }
}