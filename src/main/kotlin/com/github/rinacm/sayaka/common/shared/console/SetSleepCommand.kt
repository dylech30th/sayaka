package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.SetSleepCommandHandler
import com.github.rinacm.sayaka.common.message.translator.SetSleepCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(SetSleepCommandTranslator::class, SetSleepCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(PurePlainTextValidator::class)
@WithPrivilege(Privilege.ADMINISTRATOR)
class SetSleepCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<SetSleepCommand> {
        override val match: String = "/sleep"
        override val description: String = "命令bot进入休眠状态"
    }
}