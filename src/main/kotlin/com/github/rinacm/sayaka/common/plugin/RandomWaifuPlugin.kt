package com.github.rinacm.sayaka.common.plugin

class RandomWaifuPlugin : Plugin<RandomWaifuPlugin>() {
    companion object Key : Plugin.Key<RandomWaifuPlugin> {
        override var enabled: Boolean = true
        override var excludes: MutableMap<String, MutableSet<ExcludeType>> = mutableMapOf()
        override val match: String = "随机色图"
        override val description: String = "从Pixiv的每日榜单中随机抽选色图"
    }
}