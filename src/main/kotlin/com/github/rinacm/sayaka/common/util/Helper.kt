@file:Suppress("unused")

package com.github.rinacm.sayaka.common.util

import com.github.rinacm.sayaka.common.message.factory.Dispatcher
import com.google.common.collect.Multimap
import com.google.gson.Gson
import io.ktor.http.*
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.event.subscribe
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.uploadAsImage
import org.intellij.lang.annotations.Language
import java.io.File
import java.lang.reflect.Member
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import java.time.Duration
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KFunction
import kotlin.reflect.full.createInstance

@Language("RegExp")
const val QQ_ID_REGEX = "(?!0+\\d*)\\d+"

fun String.toPath(): Path = Paths.get(this)

fun String.toAbsolutePath(): Path = toPath().toAbsolutePath()

fun Path.mkdirs() = File(toString()).mkdirs()

fun durationNow(): Duration = Duration.ofMillis(System.currentTimeMillis())

val Member.kotlinDeclaringClass get() = declaringClass.kotlin

fun <T: R, R> T.runIf(boolean: Boolean, block: T.() -> R): R {
    return if (boolean) block() else this
}

fun <T> buildListImmutable(block: MutableList<T>.() -> Unit): List<T> {
    return mutableListOf<T>().apply { block(this) }
}

fun <T> buildListMutable(block: MutableList<T>.() -> Unit): MutableList<T> {
    return mutableListOf<T>().apply { block(this) }
}

infix fun String.followedBy(content: MessageChain): MessageChain {
    return PlainText(this) + content
}

fun StringBuilder.appendIndent(content: String, indentLevel: Int = 1, indent: Int = 4): StringBuilder {
    append(String(CharArray(indentLevel * indent) { ' ' }))
    append(content)
    return this
}

fun StringBuilder.appendLineIndent(content: String, indentLevel: Int = 1, indent: Int = 4): StringBuilder {
    append(String(CharArray(indentLevel * indent) { ' ' }))
    append(content)
    appendLine()
    return this
}

fun indentString(content: String, indentLevel: Int = 1, indent: Int = 4): String {
    return buildString {
        appendIndent(content, indentLevel, indent)
    }
}

fun MessageChainBuilder.addLine(content: Message): Boolean {
    return add(content + PlainText("\n"))
}

fun MessageChainBuilder.addLine(content: String) {
    return add("$content\n")
}

inline fun <reified A : Annotation> KAnnotatedElement.annotation(): A {
    return annotations.singleIsInstance()
}

suspend fun String.uploadAsImage(c: Contact): Image {
    return File(this).uploadAsImage(c)
}

fun MessageChain.interceptedAuthority(privilege: Privilege?): MessageChain {
    return if (privilege != null) {
        "[# $privilege PRIVILEGE GRANTED #]\n" followedBy (this + "\n[# $privilege PRIVILEGE REVOKED #]")
    } else this
}

fun String.interceptedAuthority(privilege: Privilege?): MessageChain {
    return asMessageChain().interceptedAuthority(privilege)
}

fun String.asMessageChain(): MessageChain {
    return PlainText(this).asMessageChain()
}

fun String.asSingleMessageChainList(): List<MessageChain> {
    return asMessageChain().toSingleList()
}

fun Message.asSingleMessageChainList(): List<MessageChain> {
    return asMessageChain().toSingleList()
}

fun Bot.subscribeAlwaysUnderGlobalDispatcherSuspended(block: suspend Dispatcher.(MessageEvent) -> Unit) {
    subscribeMessages {
        always {
            with(Dispatcher.Global) {
                launch {
                    block(this@always)
                }
            }
        }
    }
}

fun <T> T.toSingleList(): List<T> {
    return listOf(this)
}

inline fun <reified E : Exception> requires(value: Boolean, message: String? = null) {
    if (!value) throws<E>(message)
}

fun <T, R> notEquals(second: T, transform: (T) -> R): (T) -> Boolean {
    return { transform(it) != transform(second) }
}

inline fun <reified E : Exception> throws(message: String? = null): Nothing {
    if (message == null)
        throw E::class.createInstance()
    throw (E::class.java.getConstructor(String::class.java)).newInstance(message)
}

object JsonFactory {
    val gson: Gson = Gson()
}

inline fun <reified T> T.toJson(): String {
    return JsonFactory.gson.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return JsonFactory.gson.fromJson(this, T::class.java)
}

infix fun String.match(regex: Regex): MatchResult? = regex.find(this)

val String.Companion.EMPTY get() = ""

inline fun <reified I> Iterable<*>.singleIsInstance(): I = single { it is I } as I

operator fun <K, V> Multimap<K, V>.set(key: K, value: V) {
    put(key, value)
}

fun <E> Collection<E>.truncatePair(): Pair<E, E> {
    return take(2).run { this[0] to this[1] }
}

@Suppress("UNCHECKED_CAST")
fun <R> Iterable<*>.cast(): List<R> {
    return map { it as R }
}

@Suppress("UNCHECKED_CAST")
fun <R> Sequence<*>.seqCast(): Sequence<R> {
    return map { it as R }
}

inline fun <reified T : Annotation, R> mapAnnotation(f: KFunction<*>, mapper: (T) -> R): R {
    return mapper(f.annotation())
}

fun buildPlaceHolder(block: PlaceholderBuilder.() -> Unit): Placeholder {
    val builder = PlaceholderBuilder()
    block(builder)
    return builder.build()
}

inline fun <reified T> threadLocalOf(noinline supplier: () -> T): ThreadLocal<T> {
    return ThreadLocal.withInitial(supplier)
}

inline fun <T, R> ThreadLocal<T>.runAlso(block: T.() -> R): R {
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

object File {
    fun exists(path: String): Boolean {
        return Files.exists(path.toPath())
    }

    fun create(path: String) {
        val p = path.toPath().toAbsolutePath()
        val directory = p.parent
        if (!Files.exists(directory)) {
            Files.createDirectories(directory)
        }
        Files.createFile(p)
    }

    fun read(path: String): CompletableFuture<String> {
        return CompletableFuture.supplyAsync {
            File(path).readText(StandardCharsets.UTF_8)
        }!!
    }

    fun write(path: String, text: String) {
        if (!exists(path)) create(path)
        CompletableFuture.runAsync {
            File(path).writeText(text, StandardCharsets.UTF_8)
        }
    }
}