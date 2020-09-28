package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerSurrenderCommand
import net.mamoe.mirai.message.MessageEvent

class GomokuPlayerSurrenderCommandTranslator : AbstractMessageTranslator<GomokuPlayerSurrenderCommand>() {
    override fun translate(rawInput: MessageEvent): GomokuPlayerSurrenderCommand {
        return GomokuPlayerSurrenderCommand(rawInput)
    }
}