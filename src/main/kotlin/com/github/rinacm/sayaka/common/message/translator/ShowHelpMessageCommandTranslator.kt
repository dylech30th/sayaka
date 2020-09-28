package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.ShowHelpMessageCommand
import net.mamoe.mirai.message.MessageEvent

class ShowHelpMessageCommandTranslator : AbstractMessageTranslator<ShowHelpMessageCommand>() {
    override fun translate(rawInput: MessageEvent): ShowHelpMessageCommand {
        return ShowHelpMessageCommand(rawInput, defaultTrailingParameters(rawInput))
    }
}