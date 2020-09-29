package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.SetPluginExcludeCommandHandler
import com.github.rinacm.sayaka.common.message.translator.SetPluginExcludeCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.plugin.ExcludeType
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(SetPluginExcludeCommandTranslator::class, SetPluginExcludeCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(RegexValidator::class, "\\w+ $QQ_ID_REGEX (group|whisper)")
@WithPrivilege(Privilege.ADMINISTRATOR)
class SetPluginExcludeCommand(override val messageEvent: MessageEvent, val name: String, val id: String, val excludeType: ExcludeType) : Command {
    companion object Key : Command.Key<SetPluginExcludeCommand> {
        override val match: String = "/exclude"
        override val description: String = "将id为${Placeholder("id")}的群/好友添加进${Placeholder("plugin_name")}的黑名单"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("plugin_name")} ${Placeholder("id")} ${Placeholder("group", "whisper", option = PlaceholderOption.MUTUALLY_EXCLUSIVE)}"
        }
    }
}