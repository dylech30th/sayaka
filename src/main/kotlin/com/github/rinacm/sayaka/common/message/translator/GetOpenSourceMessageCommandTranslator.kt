package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.GetOpenSourceMessageCommand
import net.mamoe.mirai.message.MessageEvent

class GetOpenSourceMessageCommandTranslator : AbstractMessageTranslator<GetOpenSourceMessageCommand>() {
    override fun translate(rawInput: MessageEvent): GetOpenSourceMessageCommand {
        return GetOpenSourceMessageCommand(rawInput)
    }
}