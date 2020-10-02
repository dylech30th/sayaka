package com.github.rinacm.sayaka.common.init

import com.github.rinacm.sayaka.common.util.requires
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.closeAndJoin
import net.mamoe.mirai.join
import net.mamoe.mirai.utils.SilentLogger

object BotFactory {
    private lateinit var bot: Bot

    fun getInstance(): Bot {
        synchronized(this) {
            requires<RuntimeException>(BotFactory::bot.isInitialized, "the bot has not been initialized yet")
            return bot
        }
    }

    suspend fun runAndJoin(block: Bot.() -> Unit) {
        block(getInstance())
        getInstance().join()
    }

    fun login(qq: Long, password: String) {
        synchronized(this) {
            requires<RuntimeException>(!BotFactory::bot.isInitialized, "the bot has been initialized")
            runBlocking {
                bot = Bot(qq, password) {
                    randomDeviceInfo()
                }.alsoLogin()
            }
        }
    }

    fun requestShuttingDown(milliseconds: Int) {
        synchronized(this) {
            requires<RuntimeException>(BotFactory::bot.isInitialized, "the bot has not been initialized yet")
            GlobalScope.launch {
                delay(milliseconds.toLong())
                bot.closeAndJoin()
            }
        }
    }
}