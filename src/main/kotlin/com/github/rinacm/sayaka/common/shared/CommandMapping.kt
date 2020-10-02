package com.github.rinacm.sayaka.common.shared

import com.google.common.collect.HashBiMap
import net.mamoe.mirai.contact.User

object CommandMapping {
    private val mapping = mutableMapOf<User, MutableMap<String, String>>()

    fun addCommandMapping(user: User, original: String, target: String) {
        if (mapping[user] == null) {
            mapping[user] = HashBiMap.create()
        }
        mapping[user]!![target] = original
    }

    fun removeCommandMapping(user: User, target: String): Boolean {
        if (mapping[user] == null || target !in mapping[user]!!.keys) return false
        mapping[user]!!.remove(target)
        return true
    }

    fun findMappingCommandString(user: User, str: String): String? {
        if (mapping[user] == null || str !in mapping[user]!!.keys) return null
        return mapping[user]!![str]
    }
}