package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.GetAdministratorListCommand
import net.mamoe.mirai.message.MessageEvent

class GetAdministratorListCommandTranslator : AbstractMessageTranslator<GetAdministratorListCommand>() {
    override fun translate(rawInput: MessageEvent): GetAdministratorListCommand {
        return GetAdministratorListCommand(rawInput)
    }
}