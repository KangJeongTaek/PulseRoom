package com.kjt.PulseRoom.dailyMessage.dto

import com.kjt.PulseRoom.dailyMessage.model.DailyMessage
import java.time.LocalDateTime

data class DailyMessageDTO(
    val content : String,
    val crtDt : LocalDateTime ?= null,
) {
    fun toModel() : DailyMessage {
        return DailyMessage(
            content = content,
            crtDt = crtDt
        )
    }
}