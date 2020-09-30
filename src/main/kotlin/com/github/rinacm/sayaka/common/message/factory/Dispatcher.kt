package com.github.rinacm.sayaka.common.message.factory

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.Descriptor
import com.github.rinacm.sayaka.common.util.reflector.TypedSubclassesScanner
import net.mamoe.mirai.message.MessageEvent
import kotlin.reflect.full.companionObjectInstance

/**
 * Interface to dispatch received messages as a pipe and manage the request flow
 * 1. use [translateMessage] to translate a [MessageEvent] to an Command Object
 * 2. check if the [MessageEvent] comfort [thresholding]
 * 3. validation stage happens in translation stage, it will intercept all illegal
 *    messages(.i.e a message with correct command match but has no parameters), a
 *    [ValidationException] will be thrown when the illegal messages being captured
 * 4. use [dispatchMessage] to dispatch the message to the corresponds [CommandHandler]
 * and if [translateMessage] throws means there's an exception occurred during
 * the the translation stage therefore you should call the [dispatchError] to handle the
 * error message
 */
interface Dispatcher {
    interface Key<T : Dispatcher> : Descriptor {
        val instance: T
    }

    companion object {
        var Global: Dispatcher = DefaultDispatcherImpl.instance

        fun availableDispatchers(): List<Key<*>>? {
            return TypedSubclassesScanner.markedMap[Dispatcher::class]
                ?.filter { it.companionObjectInstance is Key<*> }
                ?.map { it.companionObjectInstance as Key<*> }
        }

        fun findDispatcherKeyByName(name: String): Key<*>? {
            return TypedSubclassesScanner.markedMap[Dispatcher::class]
                ?.filter { it.companionObjectInstance is Key<*> }
                ?.map { it.companionObjectInstance as Key<*> }
                ?.firstOrNull { it.match == name }
        }

    }

    suspend fun MessageEvent.translateMessage(): Command

    suspend fun <T : Command> MessageEvent.dispatchMessage(command: T)

    suspend fun MessageEvent.dispatchError(exception: Exception)

    suspend fun MessageEvent.thresholding(): Boolean
}