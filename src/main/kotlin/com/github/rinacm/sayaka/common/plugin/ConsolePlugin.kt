package com.github.rinacm.sayaka.common.plugin

class ConsolePlugin : Plugin<ConsolePlugin>() {
    companion object Key : Plugin.Key<ConsolePlugin> {
        override val match: String = "Console"
        override val description: String = "Sayaka Bot™的控制台插件"
        override var enabled: Boolean = true
        override var excludes: MutableMap<String, MutableSet<ExcludeType>> = mutableMapOf()
    }
}