package com.github.rinacm.sayaka.common.util.reflector

import com.github.rinacm.sayaka.common.util.set
import java.lang.reflect.GenericDeclaration
import kotlin.reflect.KClass

object TypedAnnotationScanner : TypedScanner<KClass<out Annotation>, GenericDeclaration>() {
    override val markedMap: Map<KClass<out Annotation>, List<GenericDeclaration>> by lazy {
        val map = mutableMapOf<KClass<out Annotation>, List<GenericDeclaration>>()
        for ((target, classes) in scanMap.asMap()) {
            for (cls in classes) {
                map[cls] = when (target!!) {
                    ScanningTarget.METHOD -> reflector.getMethodsAnnotatedWith(cls.java)
                    ScanningTarget.CLASS -> reflector.getTypesAnnotatedWith(cls.java)
                }.toList()
            }
        }
        return@lazy map
    }

    override fun registerScanner(scanningTarget: ScanningTarget, clazz: KClass<out Annotation>) {
        scanMap[scanningTarget] = clazz
    }
}