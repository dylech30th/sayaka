package com.github.rinacm.sayaka.waifu.response

import com.google.gson.annotations.SerializedName

data class DnsResponse(
    @SerializedName("Status")
    val status: Long,

    @SerializedName("TC")
    val tc: Boolean,

    @SerializedName("RD")
    val rd: Boolean,

    @SerializedName("RA")
    val ra: Boolean,

    @SerializedName("AD")
    val ad: Boolean,

    @SerializedName("CD")
    val cd: Boolean,

    @SerializedName("Question")
    val question: List<Question> = listOf(),

    @SerializedName("Answer")
    val answer: List<Answer> = listOf()
) {
    data class Answer(
        val name: String,
        val type: Long,

        @SerializedName("TTL")
        val ttl: Long,
        val data: String
    )

    data class Question(
        val name: String,
        val type: Long
    )
}
