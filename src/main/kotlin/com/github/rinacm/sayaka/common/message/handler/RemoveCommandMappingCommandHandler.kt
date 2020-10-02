package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.CommandMapping
import com.github.rinacm.sayaka.common.shared.console.RemoveCommandMappingCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class RemoveCommandMappingCommandHandler : CommandHandler<RemoveCommandMappingCommand> {
    override suspend fun process(command: RemoveCommandMappingCommand): List<MessageChain>? {
        if (CommandMapping.removeCommandMapping(command.messageEvent.sender, command.removal)) {
            return "成功移除对于${command.removal}的命令映射".asSingleMessageChainList()
        }
        return "未找到${command.removal}的任何映射".asSingleMessageChainList()
    }
}