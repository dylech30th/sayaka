package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.GetGomokuCreditCommand
import com.github.rinacm.sayaka.common.util.toSingleList
import com.github.rinacm.sayaka.gomoku.GomokuCredit
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain

class GetGomokuCreditCommandHandler : CommandHandler<GetGomokuCreditCommand> {
    override suspend fun process(command: GetGomokuCreditCommand): List<MessageChain>? {
        return buildMessageChain {
            with(GomokuCredit.getCredit(command.playerQQId)) {
                if (this != null) {
                    add("${command.playerQQId}的点数为${this}")
                } else add("${command.playerQQId}尚没有对局记录")
            }
        }.toSingleList()
    }
}