package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.pixiv.ShuffleRankOptionCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.pixiv.core.RankingEmitter
import net.mamoe.mirai.message.data.MessageChain

class ShuffleRankOptionCommandHandler : CommandHandler<ShuffleRankOptionCommand> {
    override suspend fun process(command: ShuffleRankOptionCommand): List<MessageChain>? {
        RankingEmitter.getOrCreateByContact(command.messageEvent.subject).shuffle()
        return "成功将榜单选项随机为${RankingEmitter.getOrCreateByContact(command.messageEvent.subject).current.first}".asSingleMessageChainList()
    }
}