package com.github.rinacm.sayaka.waifu.web

import com.github.rinacm.sayaka.common.util.buildListImmutable
import com.github.rinacm.sayaka.common.util.buildListMutable
import com.github.rinacm.sayaka.waifu.response.DnsResponse
import com.github.rinacm.sayaka.waifu.run
import com.github.rinacm.sayaka.waifu.threadLocalOf
import com.github.rinacm.sayaka.waifu.urlBuilder
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import okhttp3.Dns
import java.net.InetAddress
import java.net.UnknownHostException

abstract class DnsResolver : Dns {
    protected companion object {
        var queryFailed = false
        val dnsCache: ThreadLocal<MutableMap<String, List<InetAddress>>> = threadLocalOf(::mutableMapOf)

        suspend fun requestJson(hostname: String): DnsResponse {
            HttpClient(OkHttp) {
                install(JsonFeature) {
                    serializer = GsonSerializer()
                    accept(ContentType("application", "dns-json"))
                }
                install(HttpTimeout)
            }.use {
                return it.get(urlBuilder(dnsServer) {
                    parameters["ct"] = "application/dns-json"
                    parameters["cd"] = false.toString()
                    parameters["do"] = false.toString()
                    parameters["type"] = "A"
                    parameters["name"] = hostname
                }.build()) {
                    timeout {
                        requestTimeoutMillis = 5000
                    }
                }
            }
        }
    }

    override fun lookup(hostname: String): List<InetAddress> {
        if (hostname == AUTHENTICATION_DNS_HOST) {
            cacheDefault(hostname)
        }
        with(fromCache(hostname)) {
            if (this != null) return this
        }
        if (queryFailed) {
            cacheDefault(hostname)
            return default()
        }

        return try {
            val json = runBlocking { requestJson(hostname) }
            processDnsResponse(json).apply {
                if (isEmpty()) addAll(Dns.SYSTEM.lookup(hostname))
                cache(hostname, this)
            }
        } catch (e: Exception) {
            queryFailed = true
            default()
        }
    }

    private fun processDnsResponse(response: DnsResponse): MutableList<InetAddress> {
        return buildListMutable {
            for (ip in response.answer) {
                try {
                    add(InetAddress.getByName(ip.data))
                } catch (e: UnknownHostException) {
                    continue
                }
            }
        }
    }

    private fun cacheDefault(hostname: String) {
        cache(hostname, default())
    }

    private fun cache(hostname: String, ips: List<InetAddress>) {
        dnsCache.run {
            if (hostname !in keys || this[hostname].isNullOrEmpty()) {
                this[hostname] = ips
            }
        }
    }

    private fun fromCache(hostname: String): List<InetAddress>? {
        return if (dnsCache.run { hostname in keys }) {
            dnsCache.run { this[hostname] }
        } else null
    }

    protected abstract fun default(): List<InetAddress>
}

object PixivApiDnsResolver : DnsResolver() {
    override fun default(): List<InetAddress> = buildListImmutable {
        add(InetAddress.getByName("210.140.131.219"))
        add(InetAddress.getByName("210.140.131.223"))
        add(InetAddress.getByName("210.140.131.226"))
    }
}