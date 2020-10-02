package com.github.rinacm.sayaka.common.shared

import com.github.rinacm.sayaka.common.init.BotContext
import com.github.rinacm.sayaka.common.message.error.AuthorizeException
import com.github.rinacm.sayaka.common.message.validation.PurePlainTextValidator
import com.github.rinacm.sayaka.common.message.validation.RegexValidator
import com.github.rinacm.sayaka.common.plugin.ExcludeType
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.util.*
import com.github.rinacm.sayaka.common.util.reflector.TypedSubclassesScanner
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.message.MessageEvent
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.primaryConstructor

object CommandFactory {
    private val commandKeywordToCommandClassCache = ConcurrentHashMap<String, KClass<out Command>>()
    private val commandClassToContextualCache = ConcurrentHashMap<KClass<out Command>, Contextual>()

    /**
     * Lookup a [KClass] of [Command] through the [TypedSubclassesScanner.markedMap]
     * by [cmd] as the match key, if matches found then put them into a [commandKeywordToCommandClassCache] to
     * improve the query speed
     */
    fun lookup(cmd: String, user: User? = null): KClass<out Command>? {
        var key = cmd
        if (user != null) {
            CommandMapping.findMappingCommandString(user, key)?.let { key = it }
        }
        if (key in commandKeywordToCommandClassCache.keys) {
            return commandKeywordToCommandClassCache[key]!!
        }
        val obj = TypedSubclassesScanner.markedMap[Command::class]
            ?.firstOrNull { tryMatch(key, it.companionObjectInstance as Command.Key<*>?) }
            ?.companionObject
            ?: return null
        @Suppress("UNCHECKED_CAST")
        return (obj.java.declaringClass.kotlin as KClass<out Command>).apply {
            commandKeywordToCommandClassCache[key] = this
        }
    }

    /**
     * Attempting to match a [cmd] string with given [commandKey] and return the
     * matching result
     */
    private fun tryMatch(cmd: String?, commandKey: Command.Key<*>?): Boolean {
        if (cmd == null || commandKey == null) return false
        return when (commandKey.matchOption) {
            MatchOption.REG_EXP -> cmd matches commandKey.match.toRegex()
            MatchOption.PLAIN_TEXT -> cmd == commandKey.match
        }
    }

    fun getContextual(cmdClass: KClass<out Command>): Contextual {
        if (cmdClass in commandClassToContextualCache.keys) {
            return commandClassToContextualCache[cmdClass]!!
        }
        return cmdClass.annotation<Contextual>().apply {
            commandClassToContextualCache[cmdClass] = this
        }
    }

    fun String.toRawCommand(): RawCommand {
        return RawCommand(substringBefore(' ').trim(), substringAfter(' ', String.EMPTY).trim().split("\\s+".toRegex()).joinToString(" "))
    }

    fun KClass<out Command>.getAuthority(): Privilege? {
        return this.annotations.filterIsInstance<WithPrivilege>().firstOrNull()?.privilege
    }

    /**
     * There're totally three checking steps:
     * 1. check the command is marked as [PermanentlyDisable]
     * 2. check the command's plugin is enabled and does not block the sender
     * 3. check the command's [Responding] comfort the sender
     */
    fun checkAccess(cmdClass: KClass<out Command>, messageEvent: MessageEvent): Boolean {
        val key = Plugin.getPluginKeyByAnnotation(cmdClass.annotations.singleIsInstance())
        return !isPermanentlyDisabled(cmdClass) && key != null && key.enabled && checkExcludes(key, messageEvent) && canRespond(cmdClass, messageEvent)
    }

    private fun canRespond(cmdClass: KClass<out Command>, messageEvent: MessageEvent): Boolean {
        return when (cmdClass.annotations.filterIsInstance<Responding>().firstOrNull()?.type) {
            RespondingType.GROUP -> messageEvent.subject is Group
            RespondingType.WHISPER -> messageEvent.subject is Friend || messageEvent.subject is Member
            else -> true
        }
    }

    private fun checkExcludes(key: Plugin.Key<*>, messageEvent: MessageEvent): Boolean {
        val id = messageEvent.subject.id.toString()
        if (id !in key.excludes || key.excludes[id] == null) return true
        return when (messageEvent.subject) {
            is Group -> ExcludeType.GROUP !in key.excludes[id]!!
            else -> ExcludeType.WHISPER !in key.excludes[id]!!
        }
    }

    private fun isPermanentlyDisabled(cmdClass: KClass<out Command>): Boolean {
        return cmdClass.annotations.any { it is PermanentlyDisable }
    }

    suspend fun validate(raw: MessageEvent, cmdClass: KClass<out Command>, annotation: Validator) {
        val clazz = annotation.validatorClass
        for (c in clazz) {
            val primaryConstructor = c.primaryConstructor!!
            when (clazz) {
                RegexValidator::class -> primaryConstructor.call(annotation.regex, cmdClass).executeWrapped(raw)
                PurePlainTextValidator::class -> primaryConstructor.call(cmdClass).executeWrapped(raw)
            }
        }
        // validate privilege
        when (cmdClass.annotations.filterIsInstance<WithPrivilege>().firstOrNull()?.privilege) {
            Privilege.SUPERUSER -> {
                if (raw.sender.id.toString() != BotContext.getOwner()) {
                    throw AuthorizeException(Privilege.SUPERUSER)
                }
            }
            Privilege.ADMINISTRATOR -> {
                if (raw.sender.id.toString() !in BotContext.getAdmins()) {
                    throw AuthorizeException(Privilege.ADMINISTRATOR)
                }
            }
            else -> return
        }
    }
}