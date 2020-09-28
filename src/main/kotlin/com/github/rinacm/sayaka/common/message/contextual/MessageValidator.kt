package com.github.rinacm.sayaka.common.message.contextual

import com.github.rinacm.sayaka.common.message.error.ValidationException
import net.mamoe.mirai.message.MessageEvent
import kotlin.reflect.KClass

abstract class MessageValidator(val toBeValidated: KClass<*>) : WrappedExecutor<MessageEvent, Unit> {
    /**
     * Perform a validation on [MessageEvent], if the messageEvent is validated
     * this method should return normally, and if not, it should throw a
     * [ValidationException] providing the failure messageEvent
     */
    @Throws(ValidationException::class)
    abstract fun validate(messageEvent: MessageEvent)

    override suspend fun executeWrapped(parameter: MessageEvent) {
        try {
            validate(parameter)
        } catch (v: ValidationException) {
            throw v
        } catch (e: Exception) {
            throw ValidationException(e)
        }
    }
}