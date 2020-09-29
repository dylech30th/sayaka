package com.github.rinacm.sayaka.waifu.model

import com.github.rinacm.sayaka.common.util.throws
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import java.io.File

data class Illustration(
    val id: String,
    val origin: String,
    val download: String,
    val bookmark: Int,
    val title: String,
    val userName: String,
    val userId: String,
    val tags: List<String>,
    val publishDate: String,
    val viewCount: Int
) {
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun download(): File {
        val dir = File("pixiv")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val ext = origin.substringAfterLast('.')
        val f = File.createTempFile(id, ".$ext")
        val response = HttpClient(OkHttp) {
            expectSuccess = false
        }.use {
            it.get<HttpResponse>(download) {
                header("Referer", "http://www.pixiv.net")
                header("User-Agent", "Sayaka/1.0.0")
            }
        }
        if (!response.status.isSuccess()) {
            throws<IllegalArgumentException>("下载图片时遇到错误: 返回的HTTP状态码(${response.status})异常")
        }
        response.content.copyAndClose(f.writeChannel())
        return f
    }
}