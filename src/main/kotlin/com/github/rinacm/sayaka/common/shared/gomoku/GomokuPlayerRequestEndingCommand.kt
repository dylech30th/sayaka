package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.GomokuPlayerRequestEndingCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GomokuPlayerRequestEndingCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GomokuPlayerRequestEndingCommandTranslator::class, GomokuPlayerRequestEndingCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Responding(RespondingType.GROUP)
@Validator(PurePlainTextValidator::class)
class GomokuPlayerRequestEndingCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GomokuPlayerRequestEndingCommand> {
        override val match: String = "/re"
        override val description: String = "发起或加入一次结束当前五子棋对局的投票"
    }
}