package com.github.rinacm.sayaka.waifu.web

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import kotlinx.coroutines.runBlocking
import java.net.InetAddress
import java.net.Socket
import java.security.cert.X509Certificate
import java.time.ZonedDateTime
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object HttpClients {
    fun app(session: Session? = null, block: HttpClientConfig<OkHttpConfig>.() -> Unit = {}): HttpClient {
        return HttpClient(OkHttp) {
            expectSuccess = false
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            install(HttpTimeout)
            engine {
                config {
                    sslSocketFactory(
                        object : SSLSocketFactory() {
                            override fun createSocket(p0: Socket?, p1: String?, p2: Int, p3: Boolean): Socket? {
                                val addr = p0!!.inetAddress
                                if (p3) p0.close()
                                return (getDefault().createSocket(addr, p2) as SSLSocket).apply {
                                    enabledProtocols = supportedProtocols
                                }
                            }

                            override fun createSocket(p0: String?, p1: Int): Socket? = null
                            override fun createSocket(
                                p0: String?,
                                p1: Int,
                                p2: InetAddress?,
                                p3: Int
                            ): Socket? = null

                            override fun createSocket(p0: InetAddress?, p1: Int): Socket? = null
                            override fun createSocket(
                                p0: InetAddress?,
                                p1: Int,
                                p2: InetAddress?,
                                p3: Int
                            ): Socket? = null

                            override fun getDefaultCipherSuites(): Array<String> = arrayOf()
                            override fun getSupportedCipherSuites(): Array<String> = arrayOf()
                        },
                        object : X509TrustManager {
                            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                        }
                    )
                    hostnameVerifier { _, _ -> true }
                    addInterceptor {
                        val old = it.request()
                        val new = old.newBuilder()
                            .apply {
                                session?.let { addHeader("Authorization", "Bearer ${session.accessToken}") }
                            }
                            .addHeader("Accept-Language", "zh-cn")
                            .addHeader("User-Agent", "sayaka/1.0.0")
                            .build()
                        it.proceed(new)
                    }
                    addInterceptor {
                        if (session != null && ZonedDateTime.now() > session.expireIn) {
                            synchronized(HttpClients) {
                                runBlocking { Session.login(session.name, session.password) }
                            }
                        }
                        it.proceed(it.request())
                    }
                    addInterceptor {
                        val response = it.proceed(it.request())
                        val body = response.body() ?: return@addInterceptor response
                        if (!response.isSuccessful && "oauth" in body.string().toLowerCase() && session != null) {
                            synchronized(HttpClients) {
                                runBlocking { Session.login(session.name, session.password) }
                            }
                            it.proceed(it.request())
                        }
                        response
                    }
                    dns(PixivApiDnsResolver)
                }
            }
            block()
        }
    }
}