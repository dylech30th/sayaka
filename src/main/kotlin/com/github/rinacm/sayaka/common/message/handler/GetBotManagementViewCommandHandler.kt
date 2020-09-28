package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.console.GetBotManagementViewCommand
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.data.MessageChain
import kotlin.reflect.full.companionObjectInstance

class GetBotManagementViewCommandHandler : CommandHandler<GetBotManagementViewCommand> {
    override suspend fun process(command: GetBotManagementViewCommand): List<MessageChain>? {
        val plugins = Plugin.all() ?: emptyList()
        return buildString {
            appendLine("管理员列表: ")
            appendLineIndent(BotContext.getAdmins().joinToString(" ", "[", "]"))
            appendLine("黑名单: ")
            for (p in plugins) {
                val key = (p.companionObjectInstance as Plugin.Key<*>)
                if (key.excludes.isNotEmpty()) {
                    appendLineIndent("${key.canonicalizeName()}: ")
                    for ((k, v) in key.excludes) {
                        appendLineIndent("$k -> ${v.joinToString(" ", "[", "]")}", indentLevel = 2)
                    }
                }
            }
            appendLine("插件列表: (使用/help查看所有插件的用法或使用/help ${Placeholder("name")}查看某个具体插件的用法)")
            appendIndent(plugins.joinToString(",", "[", "]", transform = {
                with(it.companionObjectInstance) {
                    if (this is Plugin.Key<*>) this.canonicalizeName() else String.EMPTY
                }
            }))
        }.asSingleMessageChainList()
    }
}