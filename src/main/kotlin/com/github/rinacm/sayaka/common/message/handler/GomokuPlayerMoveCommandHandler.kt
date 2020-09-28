package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerMoveCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.common.util.followedBy
import com.github.rinacm.sayaka.common.util.uploadAsImage
import com.github.rinacm.sayaka.gomoku.GameState
import com.github.rinacm.sayaka.gomoku.GomokuGameFactory
import com.github.rinacm.sayaka.gomoku.Piece
import com.github.rinacm.sayaka.gomoku.Role
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.buildMessageChain

class GomokuPlayerMoveCommandHandler : CommandHandler<GomokuPlayerMoveCommand> {
    override suspend fun process(command: GomokuPlayerMoveCommand): List<MessageChain>? {
        val game = GomokuGameFactory.tryGetGameOrNull(command.messageEvent.subject.id.toString())
        val sender = command.messageEvent.sender.id.toString()
        if (game != null && game.isActivatedAndValid(sender) && game.isInRole(sender)) {
            val list = mutableListOf<MessageChain>()
            val dropResult = game.processGoCommand(command.messageEvent.message[PlainText]!!.content)
            with(command.messageEvent.subject) {
                when (dropResult.gameState) {
                    GameState.SUCCESS -> list.add(buildMessageChain {
                        add("${game.getRole()}棋成功落子: [${dropResult.dropPoint.x},${Piece.numberToYAxis(dropResult.dropPoint.y)}]\n")
                        add(game.getImagePath().uploadAsImage(this@with))
                    })
                    GameState.ILLEGAL_MOVEMENT -> return "哎呀，您落子的位置好像不太合理，再重新试试吧？".asSingleMessageChainList()
                    GameState.CHESSBOARD_FULL -> {
                        game.close()
                        return "棋盘上已经没有位置了，和棋！\n游戏结束！".asSingleMessageChainList()
                    }
                    GameState.IRRELEVANT_MESSAGE -> return null
                }
            }

            val win = game.isWin(dropResult.dropPoint.x, dropResult.dropPoint.y)
            with(command.messageEvent.subject as Group) {
                if (win != Role.NONE) {
                    list.add(game.getWinMessageAndDisposeGame(win))
                } else {
                    list.add("请${game.getRole()}方" followedBy (this[game.getPlayer().getByRole(game.getRole()).toLong()].at() + "棋手走子"))
                }
            }
            return list
        }
        return null
    }
}