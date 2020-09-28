package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.GetAdministratorListCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetAdministratorListCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Contextual
import com.github.rinacm.sayaka.common.util.PluginOwnership
import com.github.rinacm.sayaka.common.util.Validator
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetAdministratorListCommandTranslator::class, GetAdministratorListCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(PurePlainTextValidator::class)
class GetAdministratorListCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GetAdministratorListCommand> {
        override val match: String = "/ops"
        override val description: String = "获取在该群的管理员列表 (Bot管理员而非QQ群管理)"
    }
}