package com.kjt.PulseRoom.repository

import com.kjt.PulseRoom.model.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository:JpaRepository<Chat,Long> {
}