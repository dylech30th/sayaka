package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.console.SetAdministratorCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class SetAdministratorCommandHandler : CommandHandler<SetAdministratorCommand> {
    override suspend fun process(command: SetAdministratorCommand): List<MessageChain>? {
        return if (command.qqId.toLongOrNull() != null) {
            if (BotContext.addAdmin(command.qqId)) {
                "成功将${command.qqId}添加到管理员列表成功".asSingleMessageChainList()
            } else "${command.qqId}已经是一名管理员".asSingleMessageChainList()
        } else "参数格式错误，必须是一个正确的QQ号码".asSingleMessageChainList()
    }
}