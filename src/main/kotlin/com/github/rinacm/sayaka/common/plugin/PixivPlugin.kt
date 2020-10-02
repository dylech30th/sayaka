package com.github.rinacm.sayaka.common.plugin

class PixivPlugin : Plugin<PixivPlugin>() {
    companion object Key : Plugin.Key<PixivPlugin> {
        override var enabled: Boolean = true
        override var excludes: MutableMap<String, MutableSet<ExcludeType>> = mutableMapOf()
        override val match: String = "Pixiv"
        override val description: String = "P站相关的内置插件"
    }
}