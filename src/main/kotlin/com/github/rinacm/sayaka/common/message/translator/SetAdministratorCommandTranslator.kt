package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.SetAdministratorCommand
import net.mamoe.mirai.message.MessageEvent

class SetAdministratorCommandTranslator : AbstractMessageTranslator<SetAdministratorCommand>() {
    override fun translate(rawInput: MessageEvent): SetAdministratorCommand {
        return SetAdministratorCommand(defaultOnlyOneParameter(rawInput)!!, rawInput)
    }
}