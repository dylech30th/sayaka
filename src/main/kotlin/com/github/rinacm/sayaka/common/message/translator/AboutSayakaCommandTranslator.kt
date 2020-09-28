package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.AboutSayakaCommand
import net.mamoe.mirai.message.MessageEvent

class AboutSayakaCommandTranslator : AbstractMessageTranslator<AboutSayakaCommand>() {
    override fun translate(rawInput: MessageEvent): AboutSayakaCommand {
        return AboutSayakaCommand(rawInput)
    }
}