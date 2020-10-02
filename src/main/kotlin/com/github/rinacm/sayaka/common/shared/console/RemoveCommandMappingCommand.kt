package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.RemoveCommandMappingCommandHandler
import com.github.rinacm.sayaka.common.message.translator.RemoveCommandMappingCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Contextual
import com.github.rinacm.sayaka.common.util.Placeholder
import com.github.rinacm.sayaka.common.util.PluginOwnership
import com.github.rinacm.sayaka.common.util.Validator
import net.mamoe.mirai.message.MessageEvent

@Contextual(RemoveCommandMappingCommandTranslator::class, RemoveCommandMappingCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, regex = "\\w+")
class RemoveCommandMappingCommand(override val messageEvent: MessageEvent, val removal: String) : Command {
    companion object Key : Command.Key<RemoveCommandMappingCommand> {
        override val match: String = "/rmap"
        override val description: String = "删除对于${Placeholder("alias")}的命令映射"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("alias")}"
        }
    }
}