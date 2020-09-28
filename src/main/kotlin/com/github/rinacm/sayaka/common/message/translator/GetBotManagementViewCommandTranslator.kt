package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.GetBotManagementViewCommand
import net.mamoe.mirai.message.MessageEvent

class GetBotManagementViewCommandTranslator : AbstractMessageTranslator<GetBotManagementViewCommand>() {
    override fun translate(rawInput: MessageEvent): GetBotManagementViewCommand {
        return GetBotManagementViewCommand(rawInput)
    }
}