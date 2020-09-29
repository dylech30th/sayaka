package com.github.rinacm.sayaka.waifu.core

import com.github.rinacm.sayaka.common.util.fromJson
import com.github.rinacm.sayaka.common.util.requires
import com.github.rinacm.sayaka.common.util.throws
import com.github.rinacm.sayaka.waifu.model.Illustration
import com.github.rinacm.sayaka.waifu.response.RankingResponse
import com.github.rinacm.sayaka.waifu.web.APP_API
import com.github.rinacm.sayaka.waifu.web.HttpClients
import com.github.rinacm.sayaka.waifu.web.Session
import io.ktor.client.request.*
import net.mamoe.mirai.contact.Contact
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates

class RankingEmitter : PixivEmitter<Pair<RankOption, String>> {
    companion object {
        private val rankingEmitterMap: MutableMap<Contact, RankingEmitter> = mutableMapOf()

        fun getOrCreateByContact(c: Contact): RankingEmitter {
            if (rankingEmitterMap[c] == null) {
                rankingEmitterMap[c] = RankingEmitter()
            }
            return rankingEmitterMap[c]!!
        }
    }

    override var current: Pair<RankOption, String> = RankOption.DAY to minusLocalDateNowString()
    override val cache: MutableList<Illustration> = mutableListOf()

    var coolDown: Boolean by Delegates.observable(false) { _, _, newValue ->
        if (newValue) {
            CompletableFuture.runAsync {
                Thread.sleep(5)
                coolDown = false
            }
        }
    }

    override suspend fun emit(): Illustration {
        return cache.random().apply {
            cache.remove(this)
        }
    }

    override suspend fun construct() {
        val (rankOption, dateTime) = current
        requires<IllegalStateException>(checkDate(dateTime), "获取排名的日期至多是两天以前")
        tryGetResponse("v1/illust/ranking?filter=for_android&mode=${rankOption.alias}&date=${dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}")
            .illusts
            .map(RankingResponse.Illust::parse)
            .forEach(cache::add)
    }

    fun shuffle() {
        current = RankOption.values().random() to current.second
    }

    private suspend fun tryGetResponse(url: String): RankingResponse {
        val raw = HttpClients.app(Session.global).use {
            it.get<String>(APP_API + url) {
                header("Accept-Language", "zh-cn")
            }
        }
        val result = raw.fromJson<RankingResponse>()
        if (result.illusts.isNullOrEmpty())
            throws<IllegalArgumentException>("获取到的json是空的，也许已经到达了当前搜索项的最大页数，请尝试更换模式/日期")
        return result
    }

    private fun minusLocalDateNowString(): String {
        return LocalDate.now().atStartOfDay().minusMinutes(60 * 24 * 2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    private fun checkDate(str: String): Boolean {
        return try {
            val date = LocalDate.parse(str)
            date <= LocalDate.parse(minusLocalDateNowString())
        } catch (e: DateTimeParseException) {
            false
        }
    }
}