package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.ExemptPluginExcludeCommandHandler
import com.github.rinacm.sayaka.common.message.translator.ExemptPluginExcludeCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.plugin.ExcludeType
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(ExemptPluginExcludeCommandTranslator::class, ExemptPluginExcludeCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, "\\w+ $QQ_ID_REGEX (group|whisper)")
@WithPrivilege(Privilege.ADMINISTRATOR)
class ExemptPluginExcludeCommand(override val messageEvent: MessageEvent, val name: String, val id: String, val excludeType: ExcludeType) : Command {
    companion object Key : Command.Key<ExemptPluginExcludeCommand> {
        override val match: String = "/exempt"
        override val description: String = "将${Placeholder("id")}从${Placeholder("plugin_name")}的黑名单中移除"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("plugin_name")} ${Placeholder("id")} ${Placeholder("group", "whisper", option = PlaceholderOption.MUTUALLY_EXCLUSIVE)}"
        }
    }
}