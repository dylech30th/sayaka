package com.github.rinacm.sayaka.common.message.error

import com.github.rinacm.sayaka.common.util.Privilege

class AuthorizeException(requiredPrivilege: Privilege) : Exception() {
    override val message: String? = "你没有执行该命令所需要的${requiredPrivilege}权限"
}