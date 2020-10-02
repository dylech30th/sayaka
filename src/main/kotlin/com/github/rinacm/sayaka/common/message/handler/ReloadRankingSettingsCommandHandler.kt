package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.pixiv.ReloadRankingSettingsCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.pixiv.core.RankingEmitter
import net.mamoe.mirai.message.data.MessageChain

class ReloadRankingSettingsCommandHandler : CommandHandler<ReloadRankingSettingsCommand> {
    override suspend fun process(command: ReloadRankingSettingsCommand): List<MessageChain>? {
        return with(RankingEmitter.getOrCreateByContact(command.messageEvent.subject)) {
            reload()
            "成功重置日期为: ${current.second}".asSingleMessageChainList()
        }
    }
}