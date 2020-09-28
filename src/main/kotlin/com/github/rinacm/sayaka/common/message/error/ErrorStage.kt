package com.github.rinacm.sayaka.common.message.error

enum class ErrorStage(val localizedName: String) {
    VALIDATION("命令验证"), TRANSLATION("命令转译"), PROCESSING("命令处理")
}