package com.github.rinacm.sayaka.common.message.error

class ValidationException(cause: Exception) : PipelineException(cause) {
    override val errorStage: ErrorStage = ErrorStage.VALIDATION
}