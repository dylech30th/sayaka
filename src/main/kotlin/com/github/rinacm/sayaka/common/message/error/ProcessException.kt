package com.github.rinacm.sayaka.common.message.error

class ProcessException(cause: Exception) : PipelineException(cause) {
    override val errorStage: ErrorStage = ErrorStage.PROCESSING
}