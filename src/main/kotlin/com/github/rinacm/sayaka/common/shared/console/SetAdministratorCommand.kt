package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.SetAdministratorCommandHandler
import com.github.rinacm.sayaka.common.message.translator.SetAdministratorCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(SetAdministratorCommandTranslator::class, SetAdministratorCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, QQ_ID_REGEX)
@WithAuthorize(Authority.SUPERUSER)
data class SetAdministratorCommand(val qqId: String, override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<SetAdministratorCommand> {
        override val match: String = "/op"
        override val description: String = "将${Placeholder("id")}设置为管理员"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("id")}"
        }
    }
}