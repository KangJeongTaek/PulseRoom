package com.kjt.PulseRoom.chat.dto

import com.kjt.PulseRoom.chat.model.Chat
import java.time.LocalDateTime

data class ChatDTO(
    val content: String,
    val hostIp :String ?= null,
    val crtDt : LocalDateTime ?= null
){
    fun toModel() : Chat {
        return Chat(
            content = content,
            hostIp = hostIp ?: "unknown",
            crtDt = crtDt
        )
    }
}
