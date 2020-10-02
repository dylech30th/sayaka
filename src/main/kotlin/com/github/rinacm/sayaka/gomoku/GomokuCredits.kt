@file:Suppress("unused")

package com.github.rinacm.sayaka.gomoku

import com.github.rinacm.sayaka.common.util.*

data class PlayerCredit(val player: String, var credit: Long)

object GomokuCredit {
    private var gomokuPlayerCredits = mutableListOf<PlayerCredit>()

    private const val fileName = "player_credit.json"

    fun getCredit(player: String): Long? {
        return gomokuPlayerCredits.firstOrNull { it.player == player }?.credit
    }

    fun setOrIncreaseCredit(id: String, creditIncremental: Long) {
        if (gomokuPlayerCredits.any { it.player == id }) {
            with(gomokuPlayerCredits.single { it.player == id }) {
                credit += creditIncremental
            }
        } else gomokuPlayerCredits.add(PlayerCredit(id, creditIncremental))
    }

    fun setOrRewriteCredit(id: String, creditIncremental: Long) {
        if (gomokuPlayerCredits.any { it.player == id }) {
            with(gomokuPlayerCredits.single { it.player == id }) {
                credit = creditIncremental
            }
        } else gomokuPlayerCredits.add(PlayerCredit(id, creditIncremental))
    }


    @TriggerOn(TriggerPoint.BEFORE_CLOSED)
    fun save() {
        if (!File.exists(fileName)) {
            File.create(fileName)
            File.write(fileName, gomokuPlayerCredits.toJson())
        } else {
            val future = File.read(fileName).thenApply<List<PlayerCredit>> { it.fromJson() }
            future.thenAccept {
                val added = gomokuPlayerCredits.apply {
                    addAll(it)
                    distinctBy(PlayerCredit::player)
                }
                File.write(fileName, added.toJson())
            }
        }
    }

    @TriggerOn(TriggerPoint.STARTUP)
    fun load() {
        if (File.exists(fileName)) {
            gomokuPlayerCredits = File.read(fileName).get().fromJson()
        }
    }

    fun get(): List<PlayerCredit> {
        return gomokuPlayerCredits
    }
}