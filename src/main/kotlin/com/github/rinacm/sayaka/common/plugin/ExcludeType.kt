package com.github.rinacm.sayaka.common.plugin

enum class ExcludeType {
    GROUP, WHISPER;

    companion object {
        fun toExcludeType(str: String): ExcludeType? {
            return when (str.toLowerCase()) {
                "group" -> GROUP
                "whisper" -> WHISPER
                else -> null
            }
        }
    }
}