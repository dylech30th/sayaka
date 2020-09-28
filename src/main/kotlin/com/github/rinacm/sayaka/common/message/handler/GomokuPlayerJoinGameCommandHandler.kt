package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.plugin.GomokuPlugin
import com.github.rinacm.sayaka.common.plugin.Plugin
import com.github.rinacm.sayaka.common.shared.gomoku.GomokuPlayerJoinGameCommand
import com.github.rinacm.sayaka.common.util.addLine
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.common.util.uploadAsImage
import com.github.rinacm.sayaka.gomoku.Game
import com.github.rinacm.sayaka.gomoku.GomokuGameFactory
import com.github.rinacm.sayaka.gomoku.PlayerJoinState
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.buildMessageChain


class GomokuPlayerJoinGameCommandHandler : CommandHandler<GomokuPlayerJoinGameCommand> {
    override suspend fun process(command: GomokuPlayerJoinGameCommand): List<MessageChain>? {
        val game = GomokuGameFactory.getOrCreateGame(command.messageEvent.subject.id.toString())
        return when (game.join(command.messageEvent.sender.id.toString())) {
            PlayerJoinState.SUCCESS -> mutableListOf<MessageChain>().apply {
                add((command.messageEvent.sender as Member).at() + "成功加入！")
                if (game.isFull()) {
                    game.startGame()
                    add(getStartMessage(command.messageEvent.subject as Group, game))
                }
            }
            PlayerJoinState.HAS_JOINED -> "你已经加入过了".asSingleMessageChainList()
            PlayerJoinState.GAME_FULL -> "当前游戏已满，请等待游戏结束".asSingleMessageChainList()
        }
    }

    private suspend fun getStartMessage(group: Group, game: Game): MessageChain {
        return buildMessageChain {
            addLine("开始游戏！")
            addLine("---------------------------------")
            addLine(Plugin.help(GomokuPlugin))
            val at = group[game.getPlayer().black.toLong()].at()
            addLine(at + "是黑方先手！请" + (at + "走子"))
            add(game.getImagePath().uploadAsImage(group))
        }
    }
}