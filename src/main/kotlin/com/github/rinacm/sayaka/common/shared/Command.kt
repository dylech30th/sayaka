package com.github.rinacm.sayaka.common.shared

import com.github.rinacm.sayaka.common.util.*
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.PlainText

/**
 * Base class for all [Command], a command is an abstraction of [MessageEvent]
 * where messages can be consist of multiple parts such as [Image], [PlainText]
 * or [At], for [Command] usage there must be at least one [PlainText] message
 * to match the command name
 */
interface Command {
    val messageEvent: MessageEvent

    interface Key<T : Command> : Descriptor {
        fun help(key: Key<*>): String {
            var str = "规范: ${key.canonicalizeName()} 用途: $description"
            with(key::class.java.declaringClass.annotations) {
                this.firstOrNull { it is WithPrivilege }?.run {
                    val withAuthorize = this as WithPrivilege
                    str += when (withAuthorize.privilege) {
                        Privilege.SUPERUSER -> " (该命令仅限Bot持有者)"
                        Privilege.ADMINISTRATOR -> " (该命令仅限Bot管理员)"
                    }
                }
                this.firstOrNull { it is Responding }?.run {
                    val responding = this as Responding
                    str += when (responding.type) {
                        RespondingType.WHISPER -> " (该命令仅限私聊可用)"
                        RespondingType.GROUP -> " (该命令仅限群聊可用)"
                    }
                }
            }
            return str
        }
    }
}