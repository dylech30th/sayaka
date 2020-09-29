package com.github.rinacm.sayaka.common.shared.waifu

import com.github.rinacm.sayaka.common.message.handler.ShuffleRankOptionCommandHandler
import com.github.rinacm.sayaka.common.message.translator.ShuffleRankOptionCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.RandomWaifuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import com.github.rinacm.sayaka.waifu.core.RankOption
import net.mamoe.mirai.message.MessageEvent

@Contextual(ShuffleRankOptionCommandTranslator::class, ShuffleRankOptionCommandHandler::class)
@PluginOwnership(RandomWaifuPlugin::class)
@Validator(PurePlainTextValidator::class)
class ShuffleRankOptionCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<ShuffleRankOptionCommand> {
        override val match: String = "/rshuffle"
        override val description: String = "随机从RankOption${Placeholder(*RankOption.values().map(RankOption::alias).toTypedArray(), option = PlaceholderOption.ARRAY)}中选取一个"
    }
}