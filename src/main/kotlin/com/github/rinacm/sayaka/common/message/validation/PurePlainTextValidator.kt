package com.github.rinacm.sayaka.common.message.validation

import com.github.rinacm.sayaka.common.message.contextual.MessageValidator
import com.github.rinacm.sayaka.common.message.error.IrrelevantMessageException
import com.github.rinacm.sayaka.common.util.requires
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import kotlin.reflect.KClass

/**
 * A validator requires all [MessageEvent.message] except [MessageEvent.source]
 * must be [PlainText]
 */
open class PurePlainTextValidator(toBeValidateClass: KClass<*>) : MessageValidator(toBeValidateClass) {
    override fun validate(messageEvent: MessageEvent) {
        with(messageEvent) {
            requires<IrrelevantMessageException>(message.size == 2 && message[1] is PlainText)
        }
    }
}