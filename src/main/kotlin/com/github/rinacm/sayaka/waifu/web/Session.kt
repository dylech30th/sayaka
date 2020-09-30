package com.github.rinacm.sayaka.waifu.web

import com.github.rinacm.sayaka.common.util.md5
import com.github.rinacm.sayaka.common.util.parameter
import com.github.rinacm.sayaka.waifu.response.TokenResponse
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class Session(
    val name: String,
    val expireIn: ZonedDateTime,
    val accessToken: String,
    val mailAddress: String,
    val password: String,
    val refreshToken: String,
    val isPremium: Boolean
) {
    companion object {
        private const val CLIENT_HASH = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"
        private const val CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
        private const val CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"

        var global: Session? = null

        suspend fun login(name: String, password: String) {
            HttpClients.app().use {
                it.submitForm<TokenResponse>(oAuth.buildString(), parameter {
                    append("username", name)
                    append("password", password)
                    append("grant_type", "password")
                    append("client_id", CLIENT_ID)
                    append("client_secret", CLIENT_SECRET)
                    append("get_secure_url", 1.toString())
                }) {
                    val time = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ"))
                    header("X-Client-Hash", (time + CLIENT_HASH).md5())
                    header("X-Client-Time", time)
                }
            }.apply { resolve(this, password) }
        }

        private fun resolve(response: TokenResponse, password: String) {
            global = Session(
                response.response.user.name,
                ZonedDateTime.now().plusSeconds(response.response.expiresIn),
                response.response.accessToken,
                response.response.user.mailAddress,
                password,
                response.response.refreshToken,
                response.response.user.isPremium
            )
        }
    }
}