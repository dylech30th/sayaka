package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.console.SetGlobalDispatcherImplCommand
import net.mamoe.mirai.message.MessageEvent

class SetGlobalDispatcherImplCommandTranslator : AbstractMessageTranslator<SetGlobalDispatcherImplCommand>() {
    override fun translate(rawInput: MessageEvent): SetGlobalDispatcherImplCommand {
        return SetGlobalDispatcherImplCommand(rawInput, defaultOnlyOneParameter(rawInput)!!)
    }
}