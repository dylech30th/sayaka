package com.github.rinacm.sayaka.common.message.validation

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.error.AuthorizeException
import com.github.rinacm.sayaka.common.util.Privilege
import com.github.rinacm.sayaka.common.util.WithPrivilege
import net.mamoe.mirai.message.MessageEvent
import org.intellij.lang.annotations.Language
import kotlin.reflect.KClass

class AuthorityValidator(@Language("RegExp") private val regex: String, toBeValidated: KClass<*>) : RegexValidator(regex, toBeValidated) {
    override fun validate(messageEvent: MessageEvent) {
        super.validate(messageEvent)
        when (toBeValidated.annotations.filterIsInstance<WithPrivilege>().firstOrNull()?.privilege) {
            Privilege.SUPERUSER -> {
                if (messageEvent.sender.id.toString() != BotContext.getOwner()) {
                    throw AuthorizeException(Privilege.SUPERUSER)
                }
            }
            Privilege.ADMINISTRATOR -> {
                if (messageEvent.sender.id.toString() !in BotContext.getAdmins()) {
                    throw AuthorizeException(Privilege.ADMINISTRATOR)
                }
            }
            else -> return
        }
    }
}