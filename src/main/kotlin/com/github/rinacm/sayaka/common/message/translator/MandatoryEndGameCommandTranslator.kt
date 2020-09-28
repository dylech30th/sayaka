package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuForceEndGameCommand
import net.mamoe.mirai.message.MessageEvent

class MandatoryEndGameCommandTranslator : AbstractMessageTranslator<GomokuForceEndGameCommand>() {
    override fun translate(rawInput: MessageEvent): GomokuForceEndGameCommand {
        return GomokuForceEndGameCommand(defaultOnlyOneParameter(rawInput)!!, rawInput)
    }
}