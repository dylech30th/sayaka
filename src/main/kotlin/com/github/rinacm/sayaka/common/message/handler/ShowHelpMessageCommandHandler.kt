package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.console.ShowHelpMessageCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class ShowHelpMessageCommandHandler : CommandHandler<ShowHelpMessageCommand> {
    override suspend fun process(command: ShowHelpMessageCommand): List<MessageChain>? {
        if (command.names.isNotEmpty()) {
            return buildString {
                for (n in command.names) {
                    val plg = Plugin.getPluginKeyByName(n)
                    if (plg != null) {
                        append(Plugin.help(plg))
                    }
                }
            }.asSingleMessageChainList()
        }
        return Plugin.help().asSingleMessageChainList()
    }
}