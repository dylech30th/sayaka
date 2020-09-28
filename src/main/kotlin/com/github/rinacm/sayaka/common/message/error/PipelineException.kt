package com.github.rinacm.sayaka.common.message.error

abstract class PipelineException(val e: Exception) : RuntimeException(e) {
    abstract val errorStage: ErrorStage
}