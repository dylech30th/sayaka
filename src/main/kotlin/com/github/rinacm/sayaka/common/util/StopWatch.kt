@file:Suppress("unused")

package com.github.rinacm.sayaka.common.util

import java.time.Duration

class StopWatch {
    private var startTime = Duration.ZERO
    private var started = false

    fun start() {
        started = true
        startTime = durationNow()
    }

    fun reset() {
        startTime = durationNow()
    }

    fun stop() {
        startTime = Duration.ZERO
        started = false
    }

    fun elapsed(): Duration {
        return Duration.ofMillis(System.currentTimeMillis()).minus(startTime)
    }
}