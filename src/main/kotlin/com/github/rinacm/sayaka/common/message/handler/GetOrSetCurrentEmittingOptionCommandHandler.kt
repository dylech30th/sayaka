package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.waifu.GetOrSetCurrentEmittingOptionCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.waifu.core.RankOption
import com.github.rinacm.sayaka.waifu.core.RankingEmitter
import net.mamoe.mirai.message.data.MessageChain

class GetOrSetCurrentEmittingOptionCommandHandler : CommandHandler<GetOrSetCurrentEmittingOptionCommand> {
    override suspend fun process(command: GetOrSetCurrentEmittingOptionCommand): List<MessageChain>? {
        val emitter = RankingEmitter.getOrCreateByContact(command.messageEvent.subject)
        if (command.optionKey == null && command.optionValue == null) {
            return "榜单项: ${emitter.current.first.alias} 日期: ${emitter.current.second}".asSingleMessageChainList()
        } else if (command.optionKey != null && command.optionValue == null || command.optionKey == null && command.optionValue != null) {
            return "参数不全".asSingleMessageChainList()
        }
        when (command.optionKey) {
            "rank" -> {
                emitter.current = RankOption.valueOf(command.optionValue!!) to emitter.current.second
                emitter.cache.clear()
            }
            "date" -> {
                emitter.current = emitter.current.first to command.optionValue!!
            }
        }
        return "成功将${command.optionKey}的值设置为${command.optionValue}".asSingleMessageChainList()
    }
}