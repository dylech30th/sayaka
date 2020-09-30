package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.SetGomokuPlayerCreditCommandHandler
import com.github.rinacm.sayaka.common.message.translator.SetGomokuPlayerCreditCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@WithPrivilege(Privilege.ADMINISTRATOR)
@Contextual(SetGomokuPlayerCreditCommandTranslator::class, SetGomokuPlayerCreditCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Validator(RegexValidator::class, regex = "$QQ_ID_REGEX $QQ_ID_REGEX")
data class SetGomokuPlayerCreditCommand(val qqId: String, val amount: String, override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<SetGomokuPlayerCreditCommand> {
        override val match: String = "/gs"
        override val description: String = "设置${Placeholder("id")}的Gomoku点数"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("id")} ${Placeholder("amount")}"
        }
    }
}