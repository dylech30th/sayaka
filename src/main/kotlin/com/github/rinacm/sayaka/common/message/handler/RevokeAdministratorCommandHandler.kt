package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.console.RevokeAdministratorCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class RevokeAdministratorCommandHandler : CommandHandler<RevokeAdministratorCommand> {
    override suspend fun process(command: RevokeAdministratorCommand): List<MessageChain>? {
        return if (command.qqId.toLongOrNull() != null) {
            when {
                command.qqId == BotContext.getOwner() -> "Bot的持有者(Owner)不可以被移除出管理员列表".asSingleMessageChainList()
                BotContext.removeAdmin(command.qqId) -> "成功将${command.qqId}移出管理员列表".asSingleMessageChainList()
                else -> "${command.qqId}不在管理员列表中".asSingleMessageChainList()
            }
        } else "参数格式错误，必须是一个正确的QQ号码".asSingleMessageChainList()
    }
}