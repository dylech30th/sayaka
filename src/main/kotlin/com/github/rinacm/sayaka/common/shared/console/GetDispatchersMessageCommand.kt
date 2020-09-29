package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.GetDispatchersMessageCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetDispatchersMessageCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetDispatchersMessageCommandTranslator::class, GetDispatchersMessageCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, "(global)?")
class GetDispatchersMessageCommand(override val messageEvent: MessageEvent, val global: Boolean = false) : Command {
    companion object Key : Command.Key<GetDispatchersMessageCommand> {
        override val match: String = "/dispatchers"
        override val description: String = "查看所有可用调度器/全局调度器"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("global", option = PlaceholderOption.OPTIONAL)}"
        }
    }
}