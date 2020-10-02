package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.SetCommandMappingCommandHandler
import com.github.rinacm.sayaka.common.message.translator.SetCommandMappingCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Contextual
import com.github.rinacm.sayaka.common.util.Placeholder
import com.github.rinacm.sayaka.common.util.PluginOwnership
import com.github.rinacm.sayaka.common.util.Validator
import net.mamoe.mirai.message.MessageEvent

@Contextual(SetCommandMappingCommandTranslator::class, SetCommandMappingCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, regex = "\\w+ \\w+")
class SetCommandMappingCommand(override val messageEvent: MessageEvent, val original: String, var target: String) : Command {
    companion object Key : Command.Key<SetCommandMappingCommand> {
        override val match: String = "/map"
        override val description: String = "将给定的参数映射为某个命令"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("original")} ${Placeholder("target")}"
        }
    }
}