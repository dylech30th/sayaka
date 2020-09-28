package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerExitCommand
import net.mamoe.mirai.message.MessageEvent

class GomokuPlayerExitCommandTranslator : AbstractMessageTranslator<GomokuPlayerExitCommand>() {
    override fun translate(rawInput: MessageEvent): GomokuPlayerExitCommand {
        return GomokuPlayerExitCommand(rawInput)
    }
}