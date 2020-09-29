package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.ShutdownBotCommandHandler
import com.github.rinacm.sayaka.common.message.translator.ShutdownBotCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(ShutdownBotCommandTranslator::class, ShutdownBotCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(PurePlainTextValidator::class)
@WithPrivilege(Privilege.SUPERUSER)
class ShutdownBotCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<ShutdownBotCommand> {
        override val match: String = "/shutdown"
        override val description: String = "关闭当前Bot实例，再次使用需要重启进程"
    }
}