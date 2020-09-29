package com.github.rinacm.sayaka.gomoku

import com.github.rinacm.sayaka.common.init.BotFactory
import com.github.rinacm.sayaka.common.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import java.io.Closeable
import kotlin.random.Random
import kotlin.random.nextInt

enum class PlayerJoinState {
    SUCCESS, HAS_JOINED, GAME_FULL
}

/**
 * Every movement request of current [Game] will trigger an action
 * which will report a [GameState] to indicate the state of this movement
 */
enum class GameState {
    /** Game ended because of chessboard is full */
    CHESSBOARD_FULL,

    /** The movement request is legal */
    SUCCESS,

    /** The movement request is illegal because of the point is out of range or has already been placed by another piece */
    ILLEGAL_MOVEMENT,

    /** The movement request is illegal, which will be considered as an irrelevant request */
    IRRELEVANT_MESSAGE
}

data class DropResult(val gameState: GameState, val dropPoint: Point)

class Game(val gameId: String) : Closeable {
    abstract class Player {
        abstract val black: String
        abstract val white: String

        fun getNegative(p: String): String {
            return when (p) {
                black -> white
                white -> black
                else -> error("$p is not one of $black or $white")
            }
        }

        fun getByRole(role: Role): String {
            return when (role) {
                Role.BLACK -> black
                Role.WHITE -> white
                Role.NONE -> error("role must not be ${Role.NONE}")
            }
        }

        fun toRole(id: String): Role {
            return when (id) {
                black -> Role.BLACK
                white -> Role.WHITE
                else -> Role.NONE
            }
        }

        override fun toString(): String {
            return "($black, $white)"
        }
    }

    private class MutablePlayer : Player() {
        override lateinit var black: String
        override lateinit var white: String

        fun isBlackInitialized(): Boolean {
            return ::black.isInitialized
        }

        fun isWhiteInitialized(): Boolean {
            return ::white.isInitialized
        }

        fun switchRole() {
            if (!(isBlackInitialized() && isWhiteInitialized())) error("not initialized")
            black = white.also { white = black }
        }
    }

    // --- IN-GAME REQUIREMENTS/STATES ---
    var requestEnding = String.EMPTY to false
    private val gameRecordDirectory = GameConfiguration.gameRecordDirectory(gameId)
    private var gameStart = false
    private var imagePath = String.EMPTY
    private var chessboard: Array<Array<Piece>>

    // --- BUNDLED/EXTERNAL UTILITIES ---
    private val stopWatch = StopWatch()
    private val judgement: Judgement

    // --- PLAYERS ---
    private val player = MutablePlayer()

    // --- RECORDER FOR LAST 2 STEPS ---
    private lateinit var previousMove: Piece
    private lateinit var lastModified: Piece

    private var step = 0
    private var role = Role.BLACK

    init {
        gameRecordDirectory.toAbsolutePath().mkdirs()
        chessboard = Array(15) { Array(15) { Piece.invalid() } }
        judgement = Judgement(chessboard)
        for (i in 0 until 15) {
            for (j in 0 until 15) {
                chessboard[i][j] = Piece(i, j, Role.NONE)
            }
        }
    }

    fun join(playerId: String): PlayerJoinState {
        if (isFull()) return PlayerJoinState.GAME_FULL
        if (!player.isBlackInitialized()) {
            player.black = playerId
            return PlayerJoinState.SUCCESS
        }
        if (playerId != player.black) {
            player.white = playerId
            return PlayerJoinState.SUCCESS
        }
        return PlayerJoinState.HAS_JOINED
    }

    fun processGoCommand(command: String): DropResult {
        val (state, point) = parseCommand(command)

        if (state == GameState.SUCCESS) {
            val (success, draw) = go(point.x, point.y)
            if (success) {
                drawChessboard()
                return DropResult(if (draw) GameState.CHESSBOARD_FULL else GameState.SUCCESS, point)
            }
            return DropResult(GameState.ILLEGAL_MOVEMENT, point)
        }
        return DropResult(state, Point(-1, -1))
    }

    fun startGame() {
        stopWatch.start()
        gameStart = true
        randomizeBlack()
        drawChessboard()
    }

    fun isFull(): Boolean {
        return player.isBlackInitialized() && player.isWhiteInitialized()
    }

    fun isInRole(qq: String): Boolean {
        return player.black == qq && role == Role.BLACK || player.white == qq && role == Role.WHITE
    }

    fun isActivatedAndValid(qq: String): Boolean {
        return isFull() && isPlayer(qq)
    }

    fun isPlayer(qq: String): Boolean {
        return player.black == qq || player.white == qq
    }

    fun getImagePath(): String {
        return String(imagePath.toCharArray())
    }

    fun getPlayer(): Player {
        return player
    }

    fun getRole(): Role {
        return role
    }

    fun isWin(x: Int, y: Int): Role {
        return check(chessboard.getPoint(x, y)).apply {
            if (this != Role.NONE) stopWatch.stop()
        }
    }

    private fun check(piece: Piece): Role {
        return judgement.check(piece)
    }

    private fun randomizeBlack() {
        val i = Random.nextInt(0..1)
        if (i == 1) player.switchRole()
    }

    private fun switchRole() {
        role = role.getNegative()
    }

    private fun updateImagePath() {
        imagePath = "$gameRecordDirectory\\chessboard_${step}.jpg".toAbsolutePath().toString()
    }

    private fun drawChessboard() {
        runBlocking {
            updateImagePath()
            if (::lastModified.isInitialized) {
                Painter.save(imagePath, chessboard, lastModified)
            } else {
                Painter.save(imagePath)
            }
            delay(10)
        }
    }

    private fun parseCommand(command: String): Pair<GameState, Point> {
        val match = (command match "(?<x>\\d{1,2})(?<y>[a-oA-O])".toRegex())!!
        val xVal = match.groups["x"]?.value
        val yVal = match.groups["y"]?.value

        val illegalPoint = Point(-1, -1)

        if (xVal.isNullOrEmpty() || yVal.isNullOrEmpty()) {
            return GameState.IRRELEVANT_MESSAGE to illegalPoint
        }

        val x = xVal.toInt()
        val y = Piece.yAxisToNumber(yVal[0])

        if (x > 14) {
            return GameState.ILLEGAL_MOVEMENT to illegalPoint
        }
        return GameState.SUCCESS to Point(x, y)
    }

    /**
     * @return (successMovement, draw)
     */
    private fun go(x: Int, y: Int): Pair<Boolean, Boolean> {
        if (!validateMovement(x, y)) {
            return false to false
        }

        return when (role) {
            Role.WHITE -> whiteGo(x, y)
            Role.BLACK -> blackGo(x, y)
            Role.NONE -> throw IndexOutOfBoundsException("player role cannot be empty")
        }.let {
            if (::lastModified.isInitialized) previousMove = lastModified
            lastModified = chessboard.getPoint(x, y)
            true to isChessboardFull()
        }
    }

    private fun whiteGo(x: Int, y: Int) {
        switchRole()
        with(Piece(x, y, Role.WHITE)) {
            chessboard[x][y] = this
            lastModified = this
        }
        step++

    }

    private fun blackGo(x: Int, y: Int) {
        switchRole()
        with(Piece(x, y, Role.BLACK)) {
            chessboard[x][y] = this
            lastModified = this
        }
        step++
    }

    private fun isChessboardFull(): Boolean {
        return chessboard.flatten().all { it.kind != Role.NONE }
    }

    private fun validateMovement(x: Int, y: Int): Boolean {
        return x <= 14 && y <= 14 && chessboard[x][y].kind == Role.NONE
    }

    fun getWinMessageAndDisposeGame(win: Role): MessageChain {
        val elapsed = stopWatch.elapsed()

        val winner = player.getByRole(win)
        val loser = player.getNegative(winner)

        GomokuCredit.setOrIncreaseCredit(winner, 10000)
        GomokuCredit.setOrIncreaseCredit(loser, -10000)


        return buildMessageChain {
            val w = At(BotFactory.getInstance().getGroup(gameId.toLong())[player.getByRole(win).toLong()])
            val l = At(BotFactory.getInstance().getGroup(gameId.toLong())[player.getNegative(player.getByRole(win)).toLong()])
            add("${win.roleName}子" followedBy (w + "胜利！游戏结束！\n"))
            add("胜者" followedBy (w + "获得10000点数，现在一共有${GomokuCredit.getCredit(winner)}点数\n"))
            add("败者" followedBy (l + "扣去10000点数，现在一共有${GomokuCredit.getCredit(loser)}点数\n"))
            add("本局游戏共用时${elapsed.toHoursPart()}小时${elapsed.toMinutesPart()}分${elapsed.toSecondsPart()}秒")
        }.also { close() }
    }

    override fun close() {
        GomokuGameFactory.removeGame(gameId)
        gameStart = false
        player.black = String.EMPTY
        player.white = String.EMPTY
        stopWatch.stop()
    }
}