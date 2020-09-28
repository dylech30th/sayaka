package com.github.rinacm.sayaka.common.shared.console

import com.github.rinacm.sayaka.common.message.handler.AboutSayakaCommandHandler
import com.github.rinacm.sayaka.common.message.translator.AboutSayakaCommandTranslator
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.plugin.ConsolePlugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Contextual
import com.github.rinacm.sayaka.common.util.PluginOwnership
import com.github.rinacm.sayaka.common.util.Validator
import net.mamoe.mirai.message.MessageEvent

@Contextual(AboutSayakaCommandTranslator::class, AboutSayakaCommandHandler::class)
@PluginOwnership(ConsolePlugin::class)
@Validator(PurePlainTextValidator::class)
class AboutSayakaCommand(override val messageEvent: MessageEvent) : Command {
    companion object Key : Command.Key<AboutSayakaCommand> {
        override val match: String = "/about"
        override val description: String = "显示关于Sayaka Bot™的信息"
    }
}