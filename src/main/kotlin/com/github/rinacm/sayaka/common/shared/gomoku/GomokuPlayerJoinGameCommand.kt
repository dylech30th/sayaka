package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.GomokuPlayerJoinGameCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GomokuPlayerJoinGameCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GomokuPlayerJoinGameCommandTranslator::class, GomokuPlayerJoinGameCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Responding(RespondingType.GROUP)
@Validator(PurePlainTextValidator::class)
class GomokuPlayerJoinGameCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GomokuPlayerJoinGameCommand> {
        override val match: String = "/gj"
        override val description: String = "发起或加入五子棋对局"
    }
}