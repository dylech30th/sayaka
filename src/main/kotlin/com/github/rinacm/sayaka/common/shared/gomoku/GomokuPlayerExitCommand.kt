package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.GomokuPlayerExitCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GomokuPlayerExitCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GomokuPlayerExitCommandTranslator::class, GomokuPlayerExitCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Responding(RespondingType.GROUP)
@Validator(PurePlainTextValidator::class)
class GomokuPlayerExitCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GomokuPlayerExitCommand> {
        override val match: String = "/ge"
        override val description: String = "退出当前的五子棋对局"
    }
}