package com.github.rinacm.sayaka.waifu.core

import com.github.rinacm.sayaka.waifu.model.Illustration

interface PixivEmitter<K> {
    val current: K
    val cache: List<Illustration>

    suspend fun emit(): Illustration

    suspend fun construct()
}
