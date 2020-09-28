package com.github.rinacm.sayaka.gomoku

import com.github.rinacm.sayaka.common.util.mapAnnotation
import kotlin.reflect.KFunction

class Judgement(private val chessboard: Array<Array<Piece>>) {
    private data class Step(val leftX: Int, val leftY: Int, val rightX: Int, val rightY: Int)

    private fun getStep(attached: KFunction<*>): Step {
        val (leftX, leftY) = mapAnnotation<StepLeft, Pair<Int, Int>>(attached) { it.stepX to it.stepY }
        val (rightX, rightY) = mapAnnotation<StepRight, Pair<Int, Int>>(attached) { it.stepX to it.stepY }
        return Step(leftX, leftY, rightX, rightY)
    }

    fun check(piece: Piece): Role {
        val vertical = checkVertical(piece)
        val horizontal = checkHorizontal(piece)
        val leadingDiagonal = checkLeadingDiagonal(piece)
        val diagonal = checkDiagonal(piece)
        return when {
            vertical != Role.NONE -> vertical
            horizontal != Role.NONE -> horizontal
            leadingDiagonal != Role.NONE -> leadingDiagonal
            diagonal != Role.NONE -> diagonal
            else -> Role.NONE
        }
    }

    private fun check(piece: Piece, step: Step, kind: Role): Boolean {
        var counter = 0
        var check = piece
        while (check.x <= 14 && check.y <= 14 && check.kind == kind) {
            if (counter == 5) return true
            counter++
            try {
                check = chessboard.getPoint(check.x + step.rightX, check.y + step.rightY)
            } catch (e: IndexOutOfBoundsException) {
                break
            }
        }
        check = piece
        while (check.x >= 0 && check.y >= 0 && check.kind == kind) {
            if (counter == 5) return true
            counter++
            try {
                check = chessboard.getPoint(check.x + step.leftX, check.y + step.leftY)
            } catch (e: IndexOutOfBoundsException) {
                break
            }
        }
        return false
    }

    private fun checkBoth(piece: Piece, attached: KFunction<*>): Role {
        val step = getStep(attached)
        return when {
            check(piece, step, Role.WHITE) -> Role.WHITE
            check(piece, step, Role.BLACK) -> Role.BLACK
            else -> Role.NONE
        }
    }

    @StepLeft(0, -1)
    @StepRight(0, 1)
    private fun checkVertical(piece: Piece): Role = checkBoth(piece, ::checkVertical)

    @StepLeft(-1, 0)
    @StepRight(1, 0)
    private fun checkHorizontal(piece: Piece): Role = checkBoth(piece, ::checkHorizontal)

    @StepLeft(1, -1)
    @StepRight(-1, 1)
    private fun checkLeadingDiagonal(piece: Piece): Role = checkBoth(piece, ::checkLeadingDiagonal)

    @StepLeft(-1, -1)
    @StepRight(1, 1)
    private fun checkDiagonal(piece: Piece): Role = checkBoth(piece, ::checkDiagonal)
}