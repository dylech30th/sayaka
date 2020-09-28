package com.github.rinacm.sayaka.common.message.contextual

/**
 * Execute another function in the [executeWrapped], inspired by
 * decorator pattern but more flexible
 */
interface WrappedExecutor<in T, out R> {
    suspend fun executeWrapped(parameter: T): R
}