package com.github.rinacm.sayaka.gomoku

data class Piece(val x: Int, val y: Int, val kind: Role) {
    companion object {
        private val invalid = Piece(-1, -1, Role.NONE)
        fun invalid(): Piece = invalid

        fun yAxisToNumber(c: Char): Int {
            val u = c.toUpperCase()
            if (u in 'A'..'O') {
                return u - 'A'
            }
            throw IndexOutOfBoundsException("$c")
        }

        fun numberToYAxis(num: Int): String {
            if (num in 0 until 15) {
                return ('A' + num).toString()
            }
            throw IndexOutOfBoundsException("$num")
        }
    }

    fun toActualPoint(): Point = Point(x * 40 + 50, y * 40 + 50)
}

fun Array<Array<Piece>>.getPoint(x: Int, y: Int): Piece {
    return this[x][y]
}

enum class Role(val roleName: String) {
    BLACK("黑"), WHITE("白"), NONE("默认");

    fun getNegative(): Role {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
            NONE -> NONE
        }
    }

    override fun toString(): String {
        return roleName
    }

    fun toInt() = when (this) {
        WHITE -> 0
        BLACK -> 1
        NONE -> -1
    }
}