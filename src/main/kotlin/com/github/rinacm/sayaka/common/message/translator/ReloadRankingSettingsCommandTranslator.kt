package com.github.rinacm.sayaka.common.message.translator

import com.github.rinacm.sayaka.common.message.contextual.AbstractMessageTranslator
import com.github.rinacm.sayaka.common.shared.pixiv.ReloadRankingSettingsCommand
import net.mamoe.mirai.message.MessageEvent

class ReloadRankingSettingsCommandTranslator : AbstractMessageTranslator<ReloadRankingSettingsCommand>() {
    override fun translate(rawInput: MessageEvent): ReloadRankingSettingsCommand {
        return ReloadRankingSettingsCommand(rawInput)
    }
}