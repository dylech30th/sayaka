package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.SetSleepCommand
import net.mamoe.mirai.message.MessageEvent

class SetSleepCommandTranslator : AbstractMessageTranslator<SetSleepCommand>() {
    override fun translate(rawInput: MessageEvent): SetSleepCommand {
        return SetSleepCommand(rawInput)
    }
}