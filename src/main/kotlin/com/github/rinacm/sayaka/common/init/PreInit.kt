package com.github.rinacm.sayaka.common.init

import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.Command
import com.github.rinacm.sayaka.common.util.*
import com.github.rinacm.sayaka.common.util.reflector.ScanningTarget
import com.github.rinacm.sayaka.common.util.reflector.TypedAnnotationScanner
import com.github.rinacm.sayaka.common.util.reflector.TypedSubclassesScanner
import java.lang.reflect.Method

object PreInit {
    private fun registerVariablesGlobal() {
        TypedSubclassesScanner.registerScanner(ScanningTarget.CLASS, Command::class)
        TypedSubclassesScanner.registerScanner(ScanningTarget.CLASS, Plugin::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.METHOD, TriggerOn::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, PluginOwnership::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, Contextual::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, PermanentlyDisable::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, Validator::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, WithAuthorize::class)
        TypedAnnotationScanner.registerScanner(ScanningTarget.CLASS, Responding::class)
    }

    private fun executeTriggerPointcuts(triggerPoint: TriggerPoint) {
        val ms = TypedAnnotationScanner.markedMap[TriggerOn::class]
        if (!ms.isNullOrEmpty()) {
            val methods = ms.cast<Method>()
                .map { it.annotations.toList().singleIsInstance<TriggerOn>() to it }
                .sortedBy { it.first.priority }
                .filter { it.first.triggerPoint == triggerPoint }
                .map(Pair<TriggerOn, Method>::second)
            for (m in methods) {
                m.invoke(null)
            }
        }
    }

    fun prepared() {
        registerVariablesGlobal()
        executeTriggerPointcuts(TriggerPoint.STARTUP)
    }

    fun dispose() {
        executeTriggerPointcuts(TriggerPoint.BEFORE_CLOSED)
    }
}