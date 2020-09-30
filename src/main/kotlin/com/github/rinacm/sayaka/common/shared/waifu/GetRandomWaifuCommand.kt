package com.github.rinacm.sayaka.common.shared.waifu

import com.github.rinacm.sayaka.common.message.handler.GetRandomWaifuCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetRandomWaifuCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.RandomWaifuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Contextual
import com.github.rinacm.sayaka.common.util.PluginOwnership
import com.github.rinacm.sayaka.common.util.Validator
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetRandomWaifuCommandTranslator::class, GetRandomWaifuCommandHandler::class)
@PluginOwnership(RandomWaifuPlugin::class)
@Validator(PurePlainTextValidator::class)
class GetRandomWaifuCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<GetRandomWaifuCommand> {
        override val match: String = "/setu"
        override val description: String = "无情的随机涩图机器"
    }
}