package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.GetGomokuCreditCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.gomoku.GomokuGameFactory
import net.mamoe.mirai.message.data.MessageChain

class MandatoryEndGameCommandHandler : CommandHandler<GetGomokuCreditCommand> {
    override suspend fun process(command: GetGomokuCreditCommand): List<MessageChain>? {
        val group = command.playerQQId.toLongOrNull()
        if (group != null) {
            with(GomokuGameFactory.tryGetGameOrNull(group.toString())) {
                return if (this != null) {
                    close()
                    "成功结束对局: $gameId".asSingleMessageChainList()
                } else "参数错误或者该群暂时没有对局正在进行".asSingleMessageChainList()
            }
        } else return "参数格式错误，必须是一个正确的群聊号码".asSingleMessageChainList()
    }
}