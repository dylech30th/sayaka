package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.waifu.GetOrSetCurrentEmittingOptionCommand
import com.github.rinacm.sayaka.common.util.throws
import net.mamoe.mirai.message.MessageEvent

class GetOrSetCurrentEmittingOptionCommandTranslator : AbstractMessageTranslator<GetOrSetCurrentEmittingOptionCommand>() {
    override fun translate(rawInput: MessageEvent): GetOrSetCurrentEmittingOptionCommand {
        val parameters = defaultTrailingParameters(rawInput)
        if (parameters.isEmpty()) return GetOrSetCurrentEmittingOptionCommand(rawInput, null, null)
        if (parameters.size != 2) throws<IllegalArgumentException>()
        return GetOrSetCurrentEmittingOptionCommand(rawInput, parameters[0], parameters[1])
    }
}