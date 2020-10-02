package com.github.rinacm.sayaka.common.init

import com.github.rinacm.sayaka.common.config.SayakaConfiguration
import com.github.rinacm.sayaka.common.util.*
import com.github.rinacm.sayaka.pixiv.web.Session
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.runBlocking
import java.nio.file.Paths
import java.time.LocalDateTime

@Suppress("unused")
object BotContext {
    private var administrators = mutableSetOf<String>()
    private lateinit var configuration: SayakaConfiguration
    private lateinit var botOwner: String

    fun getCrashReportPath(): String {
        return Paths.get(configuration.crashReportPath, "ExceptionDump-${LocalDateTime.now().toString().replace(":", "-").replace(".", "-")}.txt").toAbsolutePath().toString()
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

    @TriggerOn(TriggerPoint.STARTUP, TriggerPriority.HIGHEST)
    private fun loadConfigurationAndLogin() {
        if (!File.exists(SayakaConfiguration.SAYAKA_CONFIGURATION_PATH))
            throws<IllegalStateException>("config.json must be created")
        configuration = SayakaConfiguration.read()
        administrators.addAll(configuration.admins)
        botOwner = configuration.owner
        administrators.add(botOwner)
        BotFactory.login(configuration.account.toLong(), configuration.password)
    }

    @TriggerOn(TriggerPoint.STARTUP, TriggerPriority.HIGH)
    private fun login() {
        runBlocking {
            Session.login(configuration.pixivAccount, configuration.pixivPassword)
        }
    }

    @TriggerOn(TriggerPoint.BEFORE_CLOSED)
    private fun saveConfiguration() {
        SayakaConfiguration.save(configuration)
    }
}