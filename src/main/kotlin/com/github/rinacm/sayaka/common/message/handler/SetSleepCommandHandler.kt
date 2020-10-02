package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.message.factory.Dispatcher
import com.github.rinacm.sayaka.common.shared.console.SetSleepCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class SetSleepCommandHandler : CommandHandler<SetSleepCommand> {
    override suspend fun process(command: SetSleepCommand): List<MessageChain>? {
        Dispatcher.Sleeping = true
        return "成功进入休眠状态，请管理员使用/awake指令唤醒".asSingleMessageChainList()
    }
}