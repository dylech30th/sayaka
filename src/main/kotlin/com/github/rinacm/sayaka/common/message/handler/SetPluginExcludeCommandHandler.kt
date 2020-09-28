package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.console.SetPluginExcludeCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class SetPluginExcludeCommandHandler : CommandHandler<SetPluginExcludeCommand> {
    override suspend fun process(command: SetPluginExcludeCommand): List<MessageChain>? {
        val key = Plugin.getPluginKeyByName(command.name)
        if (key != null) {
            val set = key.excludes[command.id]
            if (set == null) {
                key.excludes[command.id] = mutableSetOf(command.excludeType)
            } else set.add(command.excludeType)
            return "成功将${command.id}(${command.excludeType})添加到${key.canonicalizeName()}的黑名单中".asSingleMessageChainList()
        }
        return "未找到插件${command.name}".asSingleMessageChainList()
    }
}