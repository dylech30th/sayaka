package com.github.rinacm.sayaka.common.shared.gomoku

import com.github.rinacm.sayaka.common.message.handler.GomokuPlayerMoveCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GomokuPlayerMoveCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent
import org.intellij.lang.annotations.Language

@Contextual(GomokuPlayerMoveCommandTranslator::class, GomokuPlayerMoveCommandHandler::class)
@PluginOwnership(GomokuPlugin::class)
@Responding(RespondingType.GROUP)
@Validator(PurePlainTextValidator::class)
class GomokuPlayerMoveCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GomokuPlayerMoveCommand> {
        @Language("RegExp")
        override val match: String = "^\\d{1,2}[a-oA-O]$"
        override val description: String = "在当前五子棋对局中走一步子"
        override val matchOption: MatchOption get() = MatchOption.REG_EXP

        override fun canonicalizeName(): String {
            return "${Placeholder("x坐标y坐标")} 例: 1a, 3h"
        }
    }
}