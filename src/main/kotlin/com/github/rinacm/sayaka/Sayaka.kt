package com.github.rinacm.sayaka

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.init.BotFactory
import com.github.rinacm.sayaka.common.init.PreInit
import com.github.rinacm.sayaka.common.message.factory.Dispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join

fun main() = runBlocking {
    PreInit.prepared()
    val config = BotContext.getLoginConfiguration()
    BotFactory.login(config[0].toLong(), config[1])
    BotFactory.getInstance().subscribeMessages {
        always {
            with(Dispatcher.Global) {
                launch {
                    try {
                        val msg = translateMessage()
                        dispatchMessage(msg)
                    } catch (e: Exception) {
                        dispatchError(e)
                    }
                }
            }
        }
    }
    BotFactory.getInstance().join()
    PreInit.dispose()
}