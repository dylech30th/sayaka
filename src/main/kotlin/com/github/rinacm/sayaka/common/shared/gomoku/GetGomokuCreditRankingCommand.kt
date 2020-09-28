package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.GetGomokuCreditRankingCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetGomokuCreditRankingCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetGomokuCreditRankingCommandTranslator::class, GetGomokuCreditRankingCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Responding(RespondingType.GROUP)
@Validator(PurePlainTextValidator::class)
class GetGomokuCreditRankingCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GetGomokuCreditRankingCommand> {
        override val match: String = "/gr"
        override val description: String = "获取当前群的Gomoku点数排名"
    }
}