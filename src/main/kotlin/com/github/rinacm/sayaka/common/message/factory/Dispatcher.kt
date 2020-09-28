package com.github.rinacm.sayaka.common.message.factory

import com.github.rinacm.sayaka.common.shared.Command
import net.mamoe.mirai.message.MessageEvent

/**
 * Interface to dispatch received messages as a pipe
 * 1. use [translateMessage] to translate a [MessageEvent] to an Command Object
 * 2. validation stage happens in translation stage, it will intercept all illegal
 *    messages(.i.e a message with correct command match but has no parameters), a
 *    [ValidationException] will be thrown when the illegal messages being captured
 * 3. use [dispatchMessage] to dispatch the message to the corresponds [CommandHandler]
 * and if [translateMessage] throws means there's an exception occurred during
 * the the translation stage therefore you should call the [dispatchError] to handle the
 * error message
 */
interface Dispatcher {
    companion object {
        val Default = DefaultDispatcherImpl
    }

    suspend fun MessageEvent.translateMessage(): Command

    suspend fun <T : Command> MessageEvent.dispatchMessage(command: T)

    suspend fun MessageEvent.dispatchError(exception: Exception)
}