package com.github.rinacm.sayaka

import com.github.rinacm.sayaka.common.init.BotFactory
import com.github.rinacm.sayaka.common.init.PreInit
import com.github.rinacm.sayaka.common.util.subscribeAlwaysUnderGlobalDispatcherSuspended
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    PreInit.use {
        BotFactory.runAndJoin {
            subscribeAlwaysUnderGlobalDispatcherSuspended {
                try {
                    val msg = translateMessage(it)
                    dispatchMessage(it, msg)
                } catch (e: Exception) {
                    dispatchError(it, e)
                }
            }
        }
    }
}