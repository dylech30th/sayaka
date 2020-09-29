package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.message.factory.Dispatcher
import com.github.rinacm.sayaka.common.shared.console.SetGlobalDispatcherImplCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class SetGlobalDispatcherImplCommandHandler : CommandHandler<SetGlobalDispatcherImplCommand> {
    override suspend fun process(command: SetGlobalDispatcherImplCommand): List<MessageChain>? {
        with(Dispatcher.findDispatcherKeyByName(command.name)) {
            if (this != null) {
                Dispatcher.Global = this.instance
            } else {
                return "未知的调度器${command.name}".asSingleMessageChainList()
            }
        }
        return "调度器成功设置为${command.name}".asSingleMessageChainList()
    }
}