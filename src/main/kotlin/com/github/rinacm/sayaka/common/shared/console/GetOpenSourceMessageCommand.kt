package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.GetOpenSourceMessageCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetOpenSourceMessageCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Contextual
import com.github.rinacm.sayaka.common.util.PluginOwnership
import com.github.rinacm.sayaka.common.util.Validator
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetOpenSourceMessageCommandTranslator::class, GetOpenSourceMessageCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(PurePlainTextValidator::class)
class GetOpenSourceMessageCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GetOpenSourceMessageCommand> {
        override val match: String = "/opensource"
        override val description: String = "获取Sayaka Bot™的开源许可证信息"
    }
}