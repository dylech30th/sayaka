package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.ShutdownBotCommand
import net.mamoe.mirai.message.MessageEvent

class ShutdownBotCommandTranslator : AbstractMessageTranslator<ShutdownBotCommand>() {
    override fun translate(rawInput: MessageEvent): ShutdownBotCommand {
        return ShutdownBotCommand(rawInput)
    }
}