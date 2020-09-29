package com.github.rinacm.sayaka.waifu

import io.ktor.http.*
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

inline fun <reified T> threadLocalOf(noinline supplier: () -> T): ThreadLocal<T> {
    return ThreadLocal.withInitial(supplier)
}

inline fun <T, R> ThreadLocal<T>.run(block: T.() -> R): R {
    return get().block()
}

inline fun url(builder: URLBuilder.() -> Unit): Url {
    val b = URLBuilder()
    b.builder()
    return b.build()
}

inline fun urlBuilder(b: URLBuilder = URLBuilder(), builder: URLBuilder.() -> Unit): URLBuilder {
    b.builder()
    return b
}

inline fun parameter(default: ParametersBuilder = ParametersBuilder(), b: ParametersBuilder.() -> Unit): ParametersBuilder {
    default.b()
    return default
}

inline fun parameter(b: ParametersBuilder.() -> Unit): Parameters {
    return parameter(ParametersBuilder(), b).build()
}

fun String.md5(): String {
    val dig = MessageDigest.getInstance("MD5")
    return dig.digest(toByteArray(StandardCharsets.UTF_8)).joinToString("") { "%02x".format(it) }
}