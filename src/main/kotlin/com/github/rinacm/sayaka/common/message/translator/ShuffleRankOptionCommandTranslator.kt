package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.pixiv.ShuffleRankOptionCommand
import net.mamoe.mirai.message.MessageEvent

class ShuffleRankOptionCommandTranslator : AbstractMessageTranslator<ShuffleRankOptionCommand>() {
    override fun translate(rawInput: MessageEvent): ShuffleRankOptionCommand {
        return ShuffleRankOptionCommand(rawInput)
    }
}