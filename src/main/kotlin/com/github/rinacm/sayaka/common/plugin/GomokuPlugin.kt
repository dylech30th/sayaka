package com.github.rinacm.sayaka.common.plugin

class GomokuPlugin : Plugin<GomokuPlugin>() {
    companion object Key : Plugin.Key<GomokuPlugin> {
        override val match: String = "GomokuQQ"
        override val description: String = "一个能在群里下五子棋的QQ机器人"
        override var enabled: Boolean = true
        override var excludes: MutableMap<String, MutableSet<ExcludeType>> = mutableMapOf()
    }
}