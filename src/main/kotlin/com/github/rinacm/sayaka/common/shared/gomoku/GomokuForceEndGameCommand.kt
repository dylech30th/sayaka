package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.MandatoryEndGameCommandHandler
import com.github.rinacm.sayaka.common.message.translator.MandatoryEndGameCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@WithPrivilege(Privilege.ADMINISTRATOR)
@Contextual(MandatoryEndGameCommandTranslator::class, MandatoryEndGameCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Responding(RespondingType.GROUP)
@Validator(RegexValidator::class, QQ_ID_REGEX)
data class GomokuForceEndGameCommand(val groupQQId: String, override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GomokuForceEndGameCommand> {
        override val match: String = "/ef"
        override val description: String = "强制结束群${Placeholder("id")}的五子棋对局"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("id")}"
        }
    }
}