package com.github.rinacm.sayaka.common.message.handler

import com.github.rinacm.sayaka.common.message.contextual.CommandHandler
import com.github.rinacm.sayaka.common.shared.gomoku.SetGomokuPlayerCreditCommand
import com.github.rinacm.sayaka.common.util.asSingleMessageChainList
import com.github.rinacm.sayaka.gomoku.GomokuCredit
import net.mamoe.mirai.message.data.MessageChain

class SetGomokuPlayerCreditCommandHandler : CommandHandler<SetGomokuPlayerCreditCommand> {
    override suspend fun process(command: SetGomokuPlayerCreditCommand): List<MessageChain>? {
        val qqId = command.qqId.toLongOrNull()
        val amount = command.qqId.toLongOrNull()
        return if (qqId != null && amount != null) {
            GomokuCredit.setOrRewriteCredit(qqId.toString(), amount)
            "设置点数成功".asSingleMessageChainList()
        } else "参数格式错误，请仔细检查后重试".asSingleMessageChainList()
    }
}