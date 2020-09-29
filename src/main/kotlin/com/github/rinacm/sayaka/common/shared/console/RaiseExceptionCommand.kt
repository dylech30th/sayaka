package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.RaiseExceptionCommandHandler
import com.github.rinacm.sayaka.common.message.translator.RaiseExceptionCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(RaiseExceptionCommandTranslator::class, RaiseExceptionCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(PurePlainTextValidator::class)
@WithPrivilege(Privilege.ADMINISTRATOR)
class RaiseExceptionCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<RaiseExceptionCommand> {
        override val match: String = "/traise"
        override val description: String = "抛出一个被ProcessException所包装的java.lang.IllegalStateException，该命令仅用于测试"
    }
}