package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.RevokeAdministratorCommand
import net.mamoe.mirai.message.MessageEvent

class RevokeAdministratorCommandTranslator : AbstractMessageTranslator<RevokeAdministratorCommand>() {
    override fun translate(rawInput: MessageEvent): RevokeAdministratorCommand {
        return RevokeAdministratorCommand(defaultOnlyOneParameter(rawInput)!!, rawInput)
    }
}
