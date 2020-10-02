package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.pixiv.GetRandomRankingCommand
import net.mamoe.mirai.message.MessageEvent

class GetRandomRankingCommandTranslator : AbstractMessageTranslator<GetRandomRankingCommand>() {
    override fun translate(rawInput: MessageEvent): GetRandomRankingCommand {
        return GetRandomRankingCommand(rawInput)
    }
}