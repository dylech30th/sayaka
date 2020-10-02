package com.github.rinacm.sayaka.common.init

import com.github.rinacm.sayaka.common.message.factory.Dispatcher
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import com.github.rinacm.sayaka.common.util.reflector.ScanningTarget
import com.github.rinacm.sayaka.common.util.reflector.TypedAnnotationScanner
import com.github.rinacm.sayaka.common.util.reflector.TypedSubclassesScanner
import java.io.Closeable
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method
import kotlin.Exception
import kotlin.reflect.full.companionObjectInstance

object PreInit : Closeable {
    init {
        registerVariablesGlobal()
        executeTriggerPointcuts(TriggerPoint.STARTUP)
    }

    private fun registerVariablesGlobal() {
        TypedSubclassesScanner.registerScanner(ScanningTarget.CLASS, Command::class)
        TypedSubclassesScanner.registerScanner(ScanningTarget.CLASS, Plugin::class)
        TypedSubclassesScanner.registerScanner(ScanningTarget.CLASS, Dispatcher::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.METHOD, TriggerOn::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, PluginOwnership::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, Contextual::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, PermanentlyDisable::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, Validator::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, WithPrivilege::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, Responding::class)
    }

    private fun executeTriggerPointcuts(triggerPoint: TriggerPoint) {
        val ms = TypedAnnotationScanner.markedMap[TriggerOn::class]
        if (!ms.isNullOrEmpty()) {
            val methods = ms.cast<Method>()
                .asSequence()
                .map { it.annotations.toList().singleIsInstance<TriggerOn>() to it }
                .sortedBy { it.first.priority }
                .filter { it.first.triggerPoint == triggerPoint }
                .map(Pair<TriggerOn, Method>::second)
            for (m in methods) {
                m.isAccessible = true
                val lookup = MethodHandles.lookup()
                val mh = lookup.unreflect(m)
                mh.invoke(m.kotlinDeclaringClass.objectInstance)
            }
        }
    }

    override fun close() {
        executeTriggerPointcuts(TriggerPoint.BEFORE_CLOSED)
    }
}