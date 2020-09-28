package com.github.rinacm.sayaka.common.message.contextual

import com.github.rinacm.sayaka.common.message.error.ProcessException
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.shared.CommandFactory.getAuthority
import com.github.rinacm.sayaka.common.util.interceptedAuthority
import net.mamoe.mirai.message.data.MessageChain

interface CommandHandler<T : Command> : WrappedExecutor<T, Unit> {
    suspend fun process(command: T): List<MessageChain>?

    override suspend fun executeWrapped(parameter: T) {
        try {
            process(parameter)?.let {
                for (m in it) {
                    parameter.messageEvent.subject.sendMessage(m.interceptedAuthority(parameter::class.getAuthority()))
                }
            }
        } catch (e: Exception) {
            throw ProcessException(e)
        }
    }
}