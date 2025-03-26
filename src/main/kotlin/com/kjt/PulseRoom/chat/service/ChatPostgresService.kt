package com.kjt.PulseRoom.chat.service

import com.kjt.PulseRoom.chat.dto.ChatDTO
import com.kjt.PulseRoom.chat.repository.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatPostgresService(
    private val chatRepository: ChatRepository,
) {
    fun saveAllChats(chats : List<ChatDTO>){
        chatRepository.saveAll(chats.map {
            it.toModel()
        })
    }
}
