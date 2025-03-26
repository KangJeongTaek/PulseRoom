package com.kjt.PulseRoom.visit.dto

import com.kjt.PulseRoom.chat.dto.ChatDTO

data class VisitDTO(
    val nickname: String,
    val visitCount: Int,
    val hits : Int,
    val todayComment: String,
    val todayChat: List<ChatDTO>,
) {
}