package com.kjt.PulseRoom.controller

import com.kjt.PulseRoom.service.ChatService
import org.springframework.stereotype.Controller
import java.time.LocalDateTime
import com.kjt.PulseRoom.model.Chat
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ChatController(
    private val chatService: ChatService
) {
    private val logger = LoggerFactory.getLogger(ChatController::class.java)

    @MessageMapping("/sendChat")
    fun regChat(@Payload(required = true) chatDTO: ChatDTO, accessor : SimpMessageHeaderAccessor){
        var content = chatDTO.content
        val hostIp = accessor.sessionAttributes?.get("hostIp")?.toString() ?: "unknown"
        content = content.replace("<.*?>".toRegex(), "")
        content = content.replace("https?://\\S+".toRegex(),"[링크 차단]")
        content = content.replace("[<>]".toRegex(),"")
        if(content.isBlank()) throw IllegalArgumentException("빈 문자열을 보내지 마시오.")
        val now = LocalDateTime.now()
        chatService.pubChat(Chat(content = content, hostIp = hostIp, crtDt = now))
    }
    
    /* TODO : 수정 필요*/
    @SubscribeMapping("/topic/chat-history")
    fun getChatHistory() : List<Chat>{
        val messages = chatService.getChatHistory()

        return messages
    }

    @GetMapping("/get-hist")
    fun hist() :List<Chat>{
        return chatService.getChatHistory()
    }
}

data class ChatDTO(
    val content : String
)