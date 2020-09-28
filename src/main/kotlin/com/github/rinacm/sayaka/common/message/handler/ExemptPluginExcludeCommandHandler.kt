package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.console.ExemptPluginExcludeCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class ExemptPluginExcludeCommandHandler : CommandHandler<ExemptPluginExcludeCommand> {
    override suspend fun process(command: ExemptPluginExcludeCommand): List<MessageChain>? {
        val key = Plugin.getPluginKeyByName(command.name)
        if (key != null) {
            val set = key.excludes[command.id]
            if (set == null || command.excludeType !in set) {
                return "${command.id}不在${command.name}的黑名单中".asSingleMessageChainList()
            }
            set.remove(command.excludeType)
            if (set.isEmpty()) {
                key.excludes.remove(command.id)
            }
            return "成功将${command.id}(${command.excludeType})从${key.canonicalizeName()}的黑名单中移除".asSingleMessageChainList()
        }
        return "未找到插件${command.name}".asSingleMessageChainList()
    }
}