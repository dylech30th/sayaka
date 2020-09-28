package com.github.rinacm.sayaka.common.message.error

class TranslationException(cause: Exception) : PipelineException(cause) {
    override val errorStage: ErrorStage = ErrorStage.TRANSLATION
}
