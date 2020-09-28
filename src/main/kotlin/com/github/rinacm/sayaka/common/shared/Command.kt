package com.github.rinacm.sayaka.common.shared

import com.github.rinacm.sayaka.common.util.Descriptor
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

    interface Key<T : Command> : Descriptor
}