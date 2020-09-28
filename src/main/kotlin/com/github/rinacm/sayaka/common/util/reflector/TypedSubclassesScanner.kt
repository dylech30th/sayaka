package com.github.rinacm.sayaka.common.util.reflector

import com.github.rinacm.sayaka.common.util.set
import kotlin.reflect.KClass

object TypedSubclassesScanner : TypedScanner<KClass<*>, KClass<out Any>>() {
    override val markedMap: Map<KClass<*>, List<KClass<out Any>>> by lazy {
        val map = mutableMapOf<KClass<*>, List<KClass<out Any>>>()
        for ((target, classes) in scanMap.asMap()) {
            for (cls in classes) {
                if (target == ScanningTarget.CLASS) {
                    map[cls] = reflector.getSubTypesOf(cls.java).map(Class<*>::kotlin)
                }
            }
        }
        return@lazy map
    }

    override fun registerScanner(scanningTarget: ScanningTarget, clazz: KClass<*>) {
        scanMap[scanningTarget] = clazz
    }
}