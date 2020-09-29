package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.GetDispatchersMessageCommand
import net.mamoe.mirai.message.MessageEvent

class GetDispatchersMessageCommandTranslator : AbstractMessageTranslator<GetDispatchersMessageCommand>() {
    override fun translate(rawInput: MessageEvent): GetDispatchersMessageCommand {
        return GetDispatchersMessageCommand(rawInput, defaultOnlyOneParameter(rawInput) != null)
    }
}