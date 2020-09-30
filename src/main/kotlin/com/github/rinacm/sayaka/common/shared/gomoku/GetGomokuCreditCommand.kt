package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.GetGomokuCreditCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetGomokuCreditCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetGomokuCreditCommandTranslator::class, GetGomokuCreditCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Validator(RegexValidator::class, regex = QQ_ID_REGEX)
data class GetGomokuCreditCommand(val playerQQId: String, override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GetGomokuCreditCommand> {
        override val match: String = "/gc"
        override val description: String = "获取${Placeholder("id")}对应的Gomoku点数"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("id")}"
        }
    }
}