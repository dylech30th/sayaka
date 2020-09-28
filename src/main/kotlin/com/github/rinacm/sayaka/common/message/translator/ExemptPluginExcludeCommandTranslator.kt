package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.message.error.IrrelevantMessageException
import com.github.rinacm.sayaka.common.plugin.ExcludeType
import com.github.rinacm.sayaka.common.shared.console.ExemptPluginExcludeCommand
import com.github.rinacm.sayaka.common.util.throws
import net.mamoe.mirai.message.MessageEvent

class ExemptPluginExcludeCommandTranslator : AbstractMessageTranslator<ExemptPluginExcludeCommand>() {
    override fun translate(rawInput: MessageEvent): ExemptPluginExcludeCommand {
        val parameters = defaultTrailingParameters(rawInput)
        return ExemptPluginExcludeCommand(rawInput, parameters[0], parameters[1], ExcludeType.toExcludeType(parameters[2]) ?: throws<IrrelevantMessageException>())
    }
}