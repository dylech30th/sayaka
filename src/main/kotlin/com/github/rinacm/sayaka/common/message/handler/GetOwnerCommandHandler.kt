package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.console.GetOwnerCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class GetOwnerCommandHandler : CommandHandler<GetOwnerCommand> {
    override suspend fun process(command: GetOwnerCommand): List<MessageChain>? {
        return BotContext.getOwner().asSingleMessageChainList()
    }
}