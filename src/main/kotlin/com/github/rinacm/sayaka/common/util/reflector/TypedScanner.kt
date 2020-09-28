package com.github.rinacm.sayaka.common.util.reflector

import com.github.rinacm.sayaka.common.util.reflector.TypedAnnotationScanner.markedMap
import com.google.common.collect.Multimap
import com.google.common.collect.MultimapBuilder
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner

enum class ScanningTarget {
    METHOD, CLASS
}

/**
 * Provide a set of functionalities to scan through the project and get
 * a list which denotes for every condition [T] there's such a set of [R] exists
 */
abstract class TypedScanner<T : Any, R : Any> {
    companion object {
        @JvmStatic
        protected val reflector by lazy {
            Reflections("com.github.rinacm.sayaka" /* root package */, TypeAnnotationsScanner(), MethodAnnotationsScanner(), SubTypesScanner())
        }
    }

    protected val scanMap: Multimap<ScanningTarget, T> = MultimapBuilder.hashKeys().arrayListValues().build()

    abstract val markedMap: Map<T, List<R>>

    /**
     * [registerScanner] is not dynamically invokable, you must register every class before you use [markedMap]
     * this method will be invalid once you use [markedMap]
     */
    abstract fun registerScanner(scanningTarget: ScanningTarget, clazz: T)
}