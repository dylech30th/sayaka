package com.github.rinacm.sayaka.common.shared.waifu

import com.github.rinacm.sayaka.common.message.handler.GetOrSetCurrentEmittingOptionCommandHandler
import com.github.rinacm.sayaka.common.message.translator.GetOrSetCurrentEmittingOptionCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.RandomWaifuPlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(GetOrSetCurrentEmittingOptionCommandTranslator::class, GetOrSetCurrentEmittingOptionCommandHandler::class)
@PluginOwnership(RandomWaifuPlugin::class)
@Validator(RegexValidator::class, "((rank day|week|month|day_male|day_female|week_original|week_rookie)|(date \\d{4}-\\d{2}-\\d{2}))?")
class GetOrSetCurrentEmittingOptionCommand(override val messageEvent: MessageEvent, val optionKey: String?, val optionValue: String?) : Command {
    companion object Key : Command.Key<GetOrSetCurrentEmittingOptionCommand> {
        override val match: String = "/waifuoption"
        override val description: String = "获取或设置随机色图的选项(日期，榜单)"

        override fun canonicalizeName(): String {
            return "$match ${
                Placeholder(
                    """
                        ${Placeholder("rank", "date", option = PlaceholderOption.MUTUALLY_EXCLUSIVE)} ${
                        Placeholder(
                            Placeholder("rank_option_value"),
                            Placeholder("date_value"),
                            option = PlaceholderOption.MUTUALLY_EXCLUSIVE
                        )
                    }
                    """.trimIndent(),
                    option = PlaceholderOption.OPTIONAL
                )
            }"
        }
    }
}