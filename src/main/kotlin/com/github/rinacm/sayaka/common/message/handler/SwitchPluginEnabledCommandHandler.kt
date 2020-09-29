package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.console.SwitchPluginEnabledCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class SwitchPluginEnabledCommandHandler : CommandHandler<SwitchPluginEnabledCommand> {
    override suspend fun process(command: SwitchPluginEnabledCommand): List<MessageChain>? {
        val clazz = Plugin.getPluginKeyByName(command.name)
        if (clazz == ConsolePlugin) {
            return "不能禁用控制台插件".asSingleMessageChainList()
        }
        if (clazz != null) {
            return if (clazz.enabled != command.enable) {
                clazz.enabled = command.enable
                if (command.enable)
                    "成功启用${clazz.canonicalizeName()}插件".asSingleMessageChainList()
                else "成功禁用${clazz.canonicalizeName()}插件".asSingleMessageChainList()
            } else "${clazz.canonicalizeName()}的enabled属性已经是${command.enable}".asSingleMessageChainList()
        }
        return "未找到${command.name}".asSingleMessageChainList()
    }
}