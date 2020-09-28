package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.GetOwnerCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetOwnerCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Contextual
import com.github.rinacm.sayaka.common.util.PermanentlyDisable
import com.github.rinacm.sayaka.common.util.PluginOwnership
import com.github.rinacm.sayaka.common.util.Validator
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetOwnerCommandTranslator::class, GetOwnerCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@PermanentlyDisable
@Validator(PurePlainTextValidator::class)
class GetOwnerCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GetOwnerCommand> {
        override val match: String = "/owner"
        override val description: String = "获取该Bot的实际持有者"
    }
}