package com.github.rinacm.sayaka.gomoku

object GomokuGameFactory {
    private val games = mutableMapOf<String, Game>()

    @Synchronized
    fun getOrCreateGame(group: String): Game {
        if (group in games.keys) {
            return games[group]!!
        }
        return Game(group).apply { games[group] = this }
    }

    @Synchronized
    fun tryGetGameOrNull(group: String): Game? {
        if (group in games.keys) {
            return games[group]
        }
        return null
    }

    @Synchronized
    fun removeGame(gameId: String) {
        games.remove(gameId)
    }
}