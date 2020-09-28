package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerRequestEndingCommand
import net.mamoe.mirai.message.MessageEvent

class GomokuPlayerRequestEndingCommandTranslator : AbstractMessageTranslator<GomokuPlayerRequestEndingCommand>() {
    override fun translate(rawInput: MessageEvent): GomokuPlayerRequestEndingCommand {
        return GomokuPlayerRequestEndingCommand(rawInput)
    }
}