package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.RevokeAdministratorCommandHandler
import com.github.rinacm.sayaka.common.message.translator.RevokeAdministratorCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(RevokeAdministratorCommandTranslator::class, RevokeAdministratorCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, QQ_ID_REGEX)
@WithAuthorize(Authority.SUPERUSER)
data class RevokeAdministratorCommand(val qqId: String, override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<RevokeAdministratorCommand> {
        override val match: String = "/revoke"
        override val description: String = "根据${Placeholder("id")}撤销Bot管理员(非QQ群管理)"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("id")}"
        }
    }
}