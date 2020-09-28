package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerSurrenderCommand
import com.github.rinacm.sayaka.gomoku.GomokuGameFactory
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.at

class GomokuPlayerSurrenderCommandHandler : CommandHandler<GomokuPlayerSurrenderCommand> {
    override suspend fun process(command: GomokuPlayerSurrenderCommand): List<MessageChain>? {
        val game = GomokuGameFactory.tryGetGameOrNull(command.messageEvent.subject.id.toString())
        val member = command.messageEvent.sender as Member
        if (game != null && game.isActivatedAndValid(member.id.toString())) {
            val winner = game.getPlayer().getNegative(member.id.toString())
            return mutableListOf<MessageChain>().apply {
                add(member.at() + "选择了投降！")
                add(game.getWinMessageAndDisposeGame(game.getPlayer().toRole(winner)))
            }
        }
        return null
    }
}