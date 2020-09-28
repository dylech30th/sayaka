package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.init.BotFactory
import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.console.ShutdownBotCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class ShutdownBotCommandHandler : CommandHandler<ShutdownBotCommand> {
    override suspend fun process(command: ShutdownBotCommand): List<MessageChain>? {
        BotFactory.requestShuttingDown(5000)
        return "Bot实例将会在5秒钟后关闭".asSingleMessageChainList()
    }
}