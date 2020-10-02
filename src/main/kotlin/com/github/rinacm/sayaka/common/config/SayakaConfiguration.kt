package com.github.rinacm.sayaka.common.config

import com.github.rinacm.sayaka.common.util.File
import com.github.rinacm.sayaka.common.util.fromJson
import com.github.rinacm.sayaka.common.util.toJson
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

data class SayakaConfiguration(
    val account: String,
    val password: String,
    val pixivAccount: String,
    val pixivPassword: String,
    val owner: String,
    val crashReportPath: String,
    val admins: List<String>
) {
    companion object {
        const val SAYAKA_CONFIGURATION_PATH = "config.json"

        fun read(): SayakaConfiguration {
            return File.read(SAYAKA_CONFIGURATION_PATH).get().fromJson()
        }

        fun save(conf: SayakaConfiguration) {
            Files.writeString(Paths.get(SAYAKA_CONFIGURATION_PATH), conf.toJson())
        }
    }
}