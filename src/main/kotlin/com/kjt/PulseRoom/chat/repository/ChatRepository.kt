package com.kjt.PulseRoom.chat.repository

import com.kjt.PulseRoom.chat.dto.ChatDTO
import com.kjt.PulseRoom.chat.model.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository:JpaRepository<Chat,Long> {


}