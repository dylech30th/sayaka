package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.GetOwnerCommand
import net.mamoe.mirai.message.MessageEvent

class GetOwnerCommandTranslator : AbstractMessageTranslator<GetOwnerCommand>() {
    override fun translate(rawInput: MessageEvent): GetOwnerCommand {
        return GetOwnerCommand(rawInput)
    }
}