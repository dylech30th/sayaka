package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.SwitchPluginEnabledCommandHandler
import com.github.rinacm.sayaka.common.message.translator.SwitchPluginEnabledCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(SwitchPluginEnabledCommandTranslator::class, SwitchPluginEnabledCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, "\\w+ (disable|enable)")
@WithPrivilege(Privilege.ADMINISTRATOR)
class SwitchPluginEnabledCommand(override val messageEvent: MessageEvent, val name: String, val enable: Boolean) : Command {
    companion object Key : Command.Key<SwitchPluginEnabledCommand> {
        @Suppress("SpellCheckingInspection")
        override val match: String = "/setswitch"
        override val description: String = "关闭或开启一个插件"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("plugin_name")} ${Placeholder("enable", "disable", option = PlaceholderOption.MUTUALLY_EXCLUSIVE)}"
        }
    }
}