package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.message.error.IrrelevantMessageException
import com.github.rinacm.sayaka.common.shared.console.SwitchPluginEnabledCommand
import com.github.rinacm.sayaka.common.util.throws
import net.mamoe.mirai.message.MessageEvent

class SwitchPluginEnabledCommandTranslator : AbstractMessageTranslator<SwitchPluginEnabledCommand>() {
    override fun translate(rawInput: MessageEvent): SwitchPluginEnabledCommand {
        val parameters = defaultTrailingParameters(rawInput)
        return SwitchPluginEnabledCommand(rawInput, parameters[0], when (parameters[1].toLowerCase()) {
            "enabled" -> true
            "disable" -> false
            else -> throws<IrrelevantMessageException>()
        })
    }
}