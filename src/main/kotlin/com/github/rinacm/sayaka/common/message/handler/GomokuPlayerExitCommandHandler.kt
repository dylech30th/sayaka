package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerExitCommand
import com.github.rinacm.sayaka.common.util.toSingleList
import com.github.rinacm.sayaka.gomoku.GomokuCredit
import com.github.rinacm.sayaka.gomoku.GomokuGameFactory
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.buildMessageChain

class GomokuPlayerExitCommandHandler : CommandHandler<GomokuPlayerExitCommand> {
    override suspend fun process(command: GomokuPlayerExitCommand): List<MessageChain>? {
        val ctx = command.messageEvent
        val game = GomokuGameFactory.tryGetGameOrNull(ctx.subject.id.toString())
        if (game != null && game.isPlayer(ctx.sender.id.toString())) {
            val message = buildMessageChain {
                val at = (ctx.sender as Member).at()
                add(at + "离开游戏, 游戏结束!")
                if (game.isFull()) {
                    add("\n由于退赛惩罚机制, ")
                    add(at + "将会被扣除20000点数")
                }
            }
            GomokuCredit.setOrIncreaseCredit(ctx.sender.id.toString(), -20000)
            game.close()
            return message.toSingleList()
        }
        return null
    }
}