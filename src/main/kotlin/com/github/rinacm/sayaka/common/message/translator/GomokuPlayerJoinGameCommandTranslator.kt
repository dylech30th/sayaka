package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerJoinGameCommand
import net.mamoe.mirai.message.MessageEvent

class GomokuPlayerJoinGameCommandTranslator : AbstractMessageTranslator<GomokuPlayerJoinGameCommand>() {
    override fun translate(rawInput: MessageEvent): GomokuPlayerJoinGameCommand {
        return GomokuPlayerJoinGameCommand(rawInput)
    }
}