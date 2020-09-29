package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.message.factory.Dispatcher
import com.github.rinacm.sayaka.common.shared.console.GetDispatchersMessageCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain
import kotlin.reflect.full.companionObjectInstance

class GetDispatchersMessageCommandHandler : CommandHandler<GetDispatchersMessageCommand> {
    override suspend fun process(command: GetDispatchersMessageCommand): List<MessageChain>? {
        return if (command.global) {
            (Dispatcher.Global::class.companionObjectInstance as Dispatcher.Key<*>).canonicalizeName().asSingleMessageChainList()
        } else with(Dispatcher.availableDispatchers()) {
            this?.joinToString(",", "[", "]", transform = Dispatcher.Key<*>::canonicalizeName)?.asSingleMessageChainList()
                ?: "未找到任何可用的调度器".asSingleMessageChainList()
        }
    }
}