package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GetGomokuCreditRankingCommand
import net.mamoe.mirai.message.MessageEvent

class GetGomokuCreditRankingCommandTranslator : AbstractMessageTranslator<GetGomokuCreditRankingCommand>() {
    override fun translate(rawInput: MessageEvent): GetGomokuCreditRankingCommand {
        return GetGomokuCreditRankingCommand(rawInput)
    }
}