package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.GomokuPlayerSurrenderCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GomokuPlayerSurrenderCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GomokuPlayerSurrenderCommandTranslator::class, GomokuPlayerSurrenderCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Responding(RespondingType.GROUP)
@Validator(PurePlainTextValidator::class)
class GomokuPlayerSurrenderCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GomokuPlayerSurrenderCommand> {
        override val match: String = "/gf"
        override val description: String = "向当前五子棋对局中的对手投降"
    }
}