package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GetGomokuCreditCommand
import net.mamoe.mirai.message.MessageEvent

class GetGomokuCreditCommandTranslator : AbstractMessageTranslator<GetGomokuCreditCommand>() {
    override fun translate(rawInput: MessageEvent): GetGomokuCreditCommand {
        return GetGomokuCreditCommand(defaultOnlyOneParameter(rawInput)!!, rawInput)
    }
}