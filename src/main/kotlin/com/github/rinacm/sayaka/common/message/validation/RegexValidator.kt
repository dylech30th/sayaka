package com.github.rinacm.sayaka.common.message.validation

import com.github.rinacm.sayaka.common.message.error.IrrelevantMessageException
import com.github.rinacm.sayaka.common.util.EMPTY
import com.github.rinacm.sayaka.common.util.requires
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import org.intellij.lang.annotations.Language
import kotlin.reflect.KClass

/**
 * Validate whether the count of the text command's arguments comfort the [size]
 *
 * This validator requires the message must be [PlainText], thus we need to pass
 * the raw message to [PurePlainTextValidator.validate] first
 */
open class RegexValidator(@Language("RegExp") private val regex: String, toBeValidated: KClass<*>) : PurePlainTextValidator(toBeValidated) {
    override fun validate(messageEvent: MessageEvent) {
        super.validate(messageEvent)
        requires<IrrelevantMessageException>(messageEvent.message[1].content.replaceBefore(' ', String.EMPTY, String.EMPTY).trim() matches regex.toRegex())
    }
}