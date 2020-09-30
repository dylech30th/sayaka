package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.ShowHelpMessageCommandHandler
import com.github.rinacm.sayaka.common.message.translator.ShowHelpMessageCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(ShowHelpMessageCommandTranslator::class, ShowHelpMessageCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, regex = "(/?[\\u4e00-\\u9fa5a-zA-Z0-9]+ *)*")
class ShowHelpMessageCommand(override val messageEvent: MessageEvent, val names: List<String> = emptyList()) : Command {
    companion object Key : Command.Key<ShowHelpMessageCommand> {
        override val match: String = "/help"
        override val description: String = "查看Sayaka Bot™的使用说明"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("plugin_name_or_command_name(with '/' prefix)", option = PlaceholderOption.OPTIONAL, repeatable = true)}"
        }
    }
}