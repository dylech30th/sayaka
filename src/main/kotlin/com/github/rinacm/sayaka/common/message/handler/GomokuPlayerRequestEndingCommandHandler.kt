package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerRequestEndingCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.common.util.toSingleList
import com.github.rinacm.sayaka.gomoku.GomokuGameFactory
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.at

class GomokuPlayerRequestEndingCommandHandler : CommandHandler<GomokuPlayerRequestEndingCommand> {
    override suspend fun process(command: GomokuPlayerRequestEndingCommand): List<MessageChain>? {
        val ctx = command.messageEvent
        val game = GomokuGameFactory.tryGetGameOrNull(ctx.subject.id.toString())

        if (game != null && game.isActivatedAndValid(ctx.sender.id.toString())) {
            with(ctx.subject as Group) {
                val (id, requesting) = game.requestEnding
                if (requesting && id != ctx.sender.id.toString()) {
                    game.close()
                    return "投票通过，结束id为${game.gameId}的对局".asSingleMessageChainList()
                } else if (!requesting) {
                    game.requestEnding = ctx.sender.id.toString() to true
                    return (this[ctx.sender.id].at() + "发起结束对局投票！同意请输入/re").toSingleList()
                }
            }
        }
        return null
    }
}