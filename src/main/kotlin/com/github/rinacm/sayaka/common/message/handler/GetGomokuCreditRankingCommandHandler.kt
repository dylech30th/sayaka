package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.GetGomokuCreditRankingCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.gomoku.GomokuCredit
import com.github.rinacm.sayaka.gomoku.PlayerCredit
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.MessageChain

class GetGomokuCreditRankingCommandHandler : CommandHandler<GetGomokuCreditRankingCommand> {
    override suspend fun process(command: GetGomokuCreditRankingCommand): List<MessageChain>? {
        val group = command.messageEvent.subject as Group
        val sortedList = GomokuCredit.get().filter {
            group.members.any { m -> m.id.toString() == it.player }
        }.sortedByDescending(PlayerCredit::credit).take(10)
        return buildString {
            for (p in sortedList) {
                appendLine("玩家: ${group[p.player.toLong()].nick} QQ: ${p.player} 点数: ${p.credit}")
            }
        }.asSingleMessageChainList()
    }
}