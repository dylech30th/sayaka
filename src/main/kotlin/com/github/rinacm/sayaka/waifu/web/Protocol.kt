package com.github.rinacm.sayaka.waifu.web

import com.github.rinacm.sayaka.common.util.urlBuilder
import io.ktor.http.*

val dnsServer = urlBuilder {
    protocol = URLProtocol.HTTPS
    host = "1.0.0.1"
    encodedPath = "dns-query"
}

val oAuth = urlBuilder {
    protocol = URLProtocol.HTTPS
    host = "oauth.secure.pixiv.net"
    encodedPath = "/auth/token"
}

const val AUTHENTICATION_DNS_HOST = "oauth.secure.pixiv.net"

const val APP_API = "https://app-api.pixiv.net/"