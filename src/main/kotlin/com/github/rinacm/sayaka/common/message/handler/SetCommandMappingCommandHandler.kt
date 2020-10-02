package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.CommandMapping
import com.github.rinacm.sayaka.common.shared.console.SetCommandMappingCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class SetCommandMappingCommandHandler : CommandHandler<SetCommandMappingCommand> {
    override suspend fun process(command: SetCommandMappingCommand): List<MessageChain>? {
        CommandMapping.addCommandMapping(command.messageEvent.sender, command.target, command.original)
        return "成功添加映射: ${command.original} -> ${command.target}".asSingleMessageChainList()
    }
}