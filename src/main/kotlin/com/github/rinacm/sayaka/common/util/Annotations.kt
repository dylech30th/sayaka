package com.github.rinacm.sayaka.common.util

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.message.contextual.MessageTranslator
import com.github.rinacm.sayaka.common.message.contextual.MessageValidator
import com.github.rinacm.sayaka.common.message.contextual.WrappedExecutor
import com.github.rinacm.sayaka.common.plugin.Plugin
import org.intellij.lang.annotations.Language
import kotlin.reflect.KClass

enum class MatchOption {
    REG_EXP, PLAIN_TEXT
}

/**
 * Mark the contextual [MessageTranslator] and [CommandHandler] of the attached class as
 * its command pipe processor
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Contextual(val translator: KClass<out WrappedExecutor<*, *>>, val handler: KClass<out WrappedExecutor<*, Unit>>)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Validator(val validatorClass: KClass<out MessageValidator>, @Language("RegExp") val regex: String = "")

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class WithPrivilege(val privilege: Privilege)

enum class RespondingType {
    WHISPER, GROUP
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Responding(val type: RespondingType)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class PermanentlyDisable

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class PluginOwnership(val clazz: KClass<out Plugin<*>>)

enum class TriggerPoint {
    STARTUP, BEFORE_CLOSED
}

@Suppress("unused")
enum class TriggerPriority {
    HIGHEST, HIGH, NORMAL, LOW, LOWEST
}

/**
 * This annotation mark a method as dynamically invokable, the marked method will be invoked
 * at the runtime automatically based on [priority] and [triggerPoint]
 *
 * It acts directly on the method therefore you must mark the method as [JvmStatic]
 * if it is declared in an **`object`**
 * ```
 * object Singleton {
 *     @TriggerOn(TriggerPoint.STARTUP, TriggerPriority.HIGHEST)
 *     @JvmStatic
 *     fun autoInvokableMethod() { ... }
 * }
 * ```
 * @param triggerPoint indicates the when the method should be invoked
 * @param priority indicates the invoke priority, the method with higher [priority]
 *        will be executed earlier than others
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TriggerOn(val triggerPoint: TriggerPoint, val priority: TriggerPriority = TriggerPriority.NORMAL)