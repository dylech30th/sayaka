package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerMoveCommand
import net.mamoe.mirai.message.MessageEvent

class GomokuPlayerMoveCommandTranslator : AbstractMessageTranslator<GomokuPlayerMoveCommand>() {
    override fun translate(rawInput: MessageEvent): GomokuPlayerMoveCommand {
        return GomokuPlayerMoveCommand(rawInput)
    }
}
