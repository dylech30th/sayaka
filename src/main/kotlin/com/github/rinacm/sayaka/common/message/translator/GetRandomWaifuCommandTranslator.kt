package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.waifu.GetRandomWaifuCommand
import net.mamoe.mirai.message.MessageEvent

class GetRandomWaifuCommandTranslator : AbstractMessageTranslator<GetRandomWaifuCommand>() {
    override fun translate(rawInput: MessageEvent): GetRandomWaifuCommand {
        return GetRandomWaifuCommand(rawInput)
    }
}