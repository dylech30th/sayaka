package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.shared.CommandFactory
import com.github.rinacm.sayaka.common.shared.console.ShowHelpMessageCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain
import kotlin.reflect.full.companionObjectInstance

class ShowHelpMessageCommandHandler : CommandHandler<ShowHelpMessageCommand> {
    override suspend fun process(command: ShowHelpMessageCommand): List<MessageChain>? {
        val hint = "注: <>为必选参数，[]为可选参数, {1|2}意为从选择1或者2, ...意为可以重复填多个参数(用空格分隔)\n"
        if (command.names.isNotEmpty()) {
            return buildString {
                append(hint)
                for (n in command.names) {
                    if (n.startsWith('/')) {
                        val cmd = CommandFactory.lookup(n)
                        if (cmd == null) {
                            appendLine("未能找到名称为${n}的命令")
                        } else {
                            val key = cmd.companionObjectInstance as Command.Key<*>
                            appendLine(key.help(key))
                        }
                    } else {
                        val plg = Plugin.getPluginKeyByName(n)
                        append(if (plg != null) Plugin.help(plg) else "未能找到名称为${n}的插件")
                    }
                }
            }.asSingleMessageChainList()
        }
        return (hint + Plugin.help()).asSingleMessageChainList()
    }
}