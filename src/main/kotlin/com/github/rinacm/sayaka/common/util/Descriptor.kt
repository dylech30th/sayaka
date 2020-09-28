package com.github.rinacm.sayaka.common.util

interface Descriptor {
    val match: String

    val matchOption: MatchOption get() = MatchOption.PLAIN_TEXT

    val description: String

    /**
     * canonicalize string of [match], default value is [match] itself
     */
    fun canonicalizeName() = match
}