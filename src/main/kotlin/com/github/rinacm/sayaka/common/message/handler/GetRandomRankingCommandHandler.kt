package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.pixiv.GetRandomRankingCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.pixiv.core.RankingEmitter
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.uploadAsImage

class GetRandomRankingCommandHandler : CommandHandler<GetRandomRankingCommand> {
    override suspend fun process(command: GetRandomRankingCommand): List<MessageChain>? {
        with(RankingEmitter.getOrCreateByContact(command.messageEvent.subject)) {
            if (coolDown) {
                return "命令正在冷却过程中，冷却时间为3秒".asSingleMessageChainList()
            }
            coolDown = true
            if (cache.isEmpty()) {
                command.messageEvent.subject.sendMessage("正在获取新的榜单...")
                construct()
                command.messageEvent.subject.sendMessage("榜单获取完成，正在随机...")
            }
            val illustration = emit()
            command.messageEvent.subject.sendMessage("正在下载图片...")
            val file = illustration.download()
            return buildMessageChain {
                add(file.uploadAsImage(command.messageEvent.subject))
                add(buildString {
                    appendLine("作品标题: ${illustration.title}")
                    appendLine("作品Pixiv ID: ${illustration.id}")
                    appendLine("作者: ${illustration.userName}")
                    appendLine("作者ID: ${illustration.userId}")
                    appendLine("收藏数: ${illustration.bookmark}")
                    appendLine("上传日期: ${illustration.publishDate}")
                    appendLine("总浏览量: ${illustration.viewCount}")
                    appendLine("下载链接: ${illustration.origin}")
                    append("Tags: ${illustration.tags.joinToString(",", "[", "]")}")
                })
            }.asSingleMessageChainList()
        }
    }
}