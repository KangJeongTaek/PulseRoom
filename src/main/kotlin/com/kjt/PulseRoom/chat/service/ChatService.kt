package com.kjt.PulseRoom.chat.service

import com.kjt.PulseRoom.chat.dto.ChatDTO
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
@Service
class ChatService(
    private val chatRedisService: ChatRedisService,
    private val chatPostgresService : ChatPostgresService,

    ) {
    private val logger = LoggerFactory.getLogger(ChatService::class.java)

    fun publishChat(chatDTO: ChatDTO, accessor: SimpMessageHeaderAccessor) {
        var content = chatDTO.content

        val hostIp = accessor.sessionAttributes?.get("hostIp")?.toString() ?: "unknown"

        content = content.replace("<.*?>".toRegex(), "")
        content = content.replace("https?://\\S+".toRegex(),"[링크 차단]")
        content = content.replace("[<>]".toRegex(),"")

        if(content.isBlank()) throw IllegalArgumentException("빈 문자열을 보내지 마시오.")

        publishChat(ChatDTO(content = content,hostIp = hostIp, crtDt = LocalDateTime.now()))
    }


    fun publishChat(chat : ChatDTO){
        chatRedisService.publish(chat)
    }

    fun getChatHistory() :List<ChatDTO>{
        val messages = chatRedisService.getChatHistory()
        return messages
    }

    fun saveAllChats(chats : List<ChatDTO>){

        chatPostgresService.saveAllChats(chats)
    }
}