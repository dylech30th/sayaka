package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.console.GetAdministratorListCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class GetAdministratorListCommandHandler : CommandHandler<GetAdministratorListCommand> {
    override suspend fun process(command: GetAdministratorListCommand): List<MessageChain>? {
        return BotContext.getAdmins().joinToString(" ", "[", "]").asSingleMessageChainList()
    }
}