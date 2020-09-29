package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.console.RaiseExceptionCommand
import com.github.rinacm.sayaka.common.util.throws
import net.mamoe.mirai.message.data.MessageChain

class RaiseExceptionCommandHandler : CommandHandler<RaiseExceptionCommand> {
    override suspend fun process(command: RaiseExceptionCommand): List<MessageChain>? {
        throws<IllegalArgumentException>("THIS IS A TEST-PURPOSE EXCEPTION")
    }
}