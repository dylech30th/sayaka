package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.GetBotManagementViewCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetBotManagementViewCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetBotManagementViewCommandTranslator::class, GetBotManagementViewCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(PurePlainTextValidator::class)
@WithPrivilege(Privilege.ADMINISTRATOR)
class GetBotManagementViewCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GetBotManagementViewCommand> {
        override val match: String = "/view"
        override val description: String = "获取Bot当前的管理视图"
    }
}