package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.console.AboutSayakaCommand
import com.github.rinacm.sayaka.common.util.appendIndent
import com.github.rinacm.sayaka.common.util.appendLineIndent
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import net.mamoe.mirai.message.data.MessageChain

class AboutSayakaCommandHandler : CommandHandler<AboutSayakaCommand> {
    override suspend fun process(command: AboutSayakaCommand): List<MessageChain>? {
        return buildString {
            appendLine("Sayaka Bot™ - A powerful qq assistant written in Kotlin and based on Mirai Framework")
            appendLine("Copyright (C) 2020 Dylech30th, Licensed under MIT(https://en.wikipedia.org/wiki/MIT_License)")
            appendLine("Project website: https://www.github.com/Rinacm/sayaka")
            appendLine("Open Source Libraries Usage:")
            appendLineIndent("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            appendLineIndent("net.mamoe:mirai-core:1.3.1")
            appendLineIndent("net.mamoe:mirai-core-qqandroid:1.3.1")
            appendLineIndent("io.ktor:ktor-client-gson:1.3.2")
            appendLineIndent("com.google.code.gson:gson:2.8.6")
            appendLineIndent("org.reflections:reflections:0.9.12")
            appendLineIndent("com.google.guava:guava:29.0-jre")
            appendLine("Acknowledgements: ")
            appendLineIndent("Jetbrains s.r.o. for providing [Jetbrains Open Source License](https://www.jetbrains.com/community/opensource/)")
            appendIndent("mamoe/mirai for providing qq bot framework")
        }.asSingleMessageChainList()
    }
}