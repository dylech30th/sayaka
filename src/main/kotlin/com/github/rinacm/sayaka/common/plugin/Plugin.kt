package com.github.rinacm.sayaka.common.plugin

import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import com.github.rinacm.sayaka.common.util.reflector.TypedAnnotationScanner
import com.github.rinacm.sayaka.common.util.reflector.TypedSubclassesScanner
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.isSubclassOf

abstract class Plugin<T : Plugin<T>> {
    interface Key<T : Plugin<T>> : Descriptor {
        var enabled: Boolean
        var excludes: MutableMap<String, MutableSet<ExcludeType>>
    }

    companion object {
        private val pluginKeyToCommandKeyMapping by lazy {
            (TypedAnnotationScanner.markedMap[PluginOwnership::class] ?: emptyList())
                .asSequence()
                .seqCast<Class<*>>()
                .map(Class<*>::kotlin)
                .filter { it.isSubclassOf(Command::class) }
                .filter { it.companionObjectInstance is Command.Key<*> }
                .groupBy({ it.annotations.singleIsInstance<PluginOwnership>().clazz }, KClass<*>::companionObjectInstance)
                .mapKeys { (plugin, _) -> plugin.companionObjectInstance as Key<*> }
                .mapValues { (_, commands) -> commands.cast<Command.Key<*>>() }
        }

        fun getPluginKeyByName(name: String): Key<*>? {
            return all()?.firstOrNull { (it.companionObjectInstance as Key<*>).match == name }
                ?.companionObjectInstance as Key<*>?
        }

        fun getPluginKeyByAnnotation(annotation: PluginOwnership): Key<*>? {
            return annotation.clazz.companionObjectInstance as Key<*>?
        }

        fun all(): List<KClass<out Any>>? {
            return TypedSubclassesScanner.markedMap[Plugin::class]
                ?.filter { it.companionObjectInstance is Key<*> }
        }

        fun help(pluginKey: Key<*>): String {
            if (pluginKeyToCommandKeyMapping.any { (key, _) -> key == pluginKey }) {
                val commands = pluginKeyToCommandKeyMapping[pluginKey]
                return buildString {
                    appendLine("插件 ${pluginKey.canonicalizeName()}: ${pluginKey.description} ")
                    appendLineIndent("命令列表: ")
                    for (cmd in commands!!) {
                        var str = "规范: ${cmd.canonicalizeName()} 用途: ${cmd.description}"
                        with(cmd::class.java.declaringClass.annotations) {
                            this.firstOrNull { it is WithAuthorize }?.run {
                                val withAuthorize = this as WithAuthorize
                                str += when (withAuthorize.authority) {
                                    Authority.SUPERUSER -> " (该命令仅限Bot持有者)"
                                    Authority.ADMINISTRATOR -> " (该命令仅限Bot管理员)"
                                }
                            }
                        }
                        appendLineIndent(str, indentLevel = 2)
                    }
                }
            }
            return String.EMPTY
        }

        fun help(): String {
            return buildString {
                for (plugin in pluginKeyToCommandKeyMapping.keys) {
                    append(help(plugin))
                }
            }
        }
    }
}
