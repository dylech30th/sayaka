package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.SetGlobalDispatcherImplCommandHandler
import com.github.rinacm.sayaka.common.message.translator.SetGlobalDispatcherImplCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent

@Contextual(SetGlobalDispatcherImplCommandTranslator::class, SetGlobalDispatcherImplCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@WithPrivilege(Privilege.ADMINISTRATOR)
@Validator(RegexValidator::class, regex = "[a-zA-Z]+")
class SetGlobalDispatcherImplCommand(override val messageEvent: MessageEvent, val name: String) : Command {
    companion object Key : Command.Key<SetGlobalDispatcherImplCommand> {
        override val match: String = "/dispatchBy"
        override val description: String = "设置Bot的全局调度器"

        override fun canonicalizeName(): String {
            return "$match ${Placeholder("dispatcher_name")}"
        }
    }
}