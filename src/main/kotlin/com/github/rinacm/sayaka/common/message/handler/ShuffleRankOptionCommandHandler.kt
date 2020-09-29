package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.waifu.ShuffleRankOptionCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.waifu.core.RankingEmitter
import net.mamoe.mirai.message.data.MessageChain

class ShuffleRankOptionCommandHandler : CommandHandler<ShuffleRankOptionCommand> {
    override suspend fun process(command: ShuffleRankOptionCommand): List<MessageChain>? {
        RankingEmitter.getOrCreateByContact(command.messageEvent.subject).shuffle()
        return "成功随机到${RankingEmitter.getOrCreateByContact(command.messageEvent.subject).current.first}的TAG".asSingleMessageChainList()
    }
}