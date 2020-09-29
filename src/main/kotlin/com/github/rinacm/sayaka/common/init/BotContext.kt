package com.github.rinacm.sayaka.common.init

import com.github.rinacm.sayaka.common.util.*
import com.github.rinacm.sayaka.waifu.web.Session
import kotlinx.coroutines.runBlocking
import java.nio.file.Paths
import java.time.LocalDateTime

@Suppress("unused")
object BotContext {
    private var administrators = mutableSetOf<String>()
    private lateinit var botOwner: String
    private const val ADMIN_PATH = "admins.json"
    private const val OWNER_PATH = "owner.txt"
    private const val ACCOUNT_CONFIGURATION_PATH = "config.txt"
    private const val CRASH_REPORT = "crash-report"
    const val PIXIV_ACCOUNT_PATH = "pixiv.txt"

    fun getCrashReportPath(): String {
        return Paths.get(CRASH_REPORT, "ExceptionDump-${LocalDateTime.now().toString().replace(":", "-").replace(".", "-")}.txt").toAbsolutePath().toString()
    }

    fun addAdmin(qqId: String): Boolean {
        return administrators.add(qqId)
    }

    fun removeAdmin(qqId: String): Boolean {
        return administrators.remove(qqId)
    }

    fun getAdmins(): Set<String> {
        return administrators
    }

    fun getOwner(): String {
        return String(botOwner.toCharArray())
    }

    fun getLoginConfiguration(): List<String> {
        if (!File.exists(ACCOUNT_CONFIGURATION_PATH)) {
            error("${ACCOUNT_CONFIGURATION_PATH.toAbsolutePath()} must be created before use the bot")
        }
        return File.read(ACCOUNT_CONFIGURATION_PATH).get().split(" ").map(String::trim)
    }

    @TriggerOn(TriggerPoint.STARTUP, TriggerPriority.HIGH)
    @JvmStatic
    fun loadOwner() {
        botOwner = if (File.exists(OWNER_PATH)) {
            File.read(OWNER_PATH).get()
        } else error("${OWNER_PATH.toAbsolutePath()} must be created before use the bot")
    }

    @TriggerOn(TriggerPoint.BEFORE_CLOSED)
    @JvmStatic
    fun saveAdmins() {
        if (!File.exists(ADMIN_PATH)) {
            File.create(ADMIN_PATH)
        }
        File.write(ADMIN_PATH, administrators.toJson())
    }

    @TriggerOn(TriggerPoint.STARTUP)
    @JvmStatic
    fun loadAdmin() {
        if (File.exists(ADMIN_PATH)) {
            administrators = File.read(ADMIN_PATH).get().fromJson()!!
            if (administrators.isEmpty()) {
                administrators.add(botOwner)
            }
        } else administrators = mutableSetOf(botOwner)
    }

    @TriggerOn(TriggerPoint.STARTUP)
    @JvmStatic
    fun pixivLogin() {
        val text = if (File.exists(PIXIV_ACCOUNT_PATH)) {
            File.read(PIXIV_ACCOUNT_PATH).get()
        } else error("${PIXIV_ACCOUNT_PATH.toAbsolutePath()} must be created before use the bot")
        val split = text.split(' ')
        runBlocking {
            Session.login(split[0].trim(), split[1].trim())
        }
    }
}