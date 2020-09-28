package com.github.rinacm.sayaka.gomoku

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class StepLeft(val stepX: Int, val stepY: Int)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class StepRight(val stepX: Int, val stepY: Int)

