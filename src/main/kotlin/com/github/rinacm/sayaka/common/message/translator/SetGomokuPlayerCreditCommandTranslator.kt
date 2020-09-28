package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.gomoku.SetGomokuPlayerCreditCommand
import net.mamoe.mirai.message.MessageEvent

class SetGomokuPlayerCreditCommandTranslator : AbstractMessageTranslator<SetGomokuPlayerCreditCommand>() {
    override fun translate(rawInput: MessageEvent): SetGomokuPlayerCreditCommand {
        val args = defaultTrailingParameters(rawInput)
        return SetGomokuPlayerCreditCommand(args[0], args[1], rawInput)
    }
}