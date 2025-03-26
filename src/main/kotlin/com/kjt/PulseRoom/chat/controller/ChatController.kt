package com.kjt.PulseRoom.chat.controller

import com.kjt.PulseRoom.chat.dto.ChatDTO
import com.kjt.PulseRoom.chat.service.ChatService
import org.springframework.stereotype.Controller
import com.kjt.PulseRoom.common.redis.manager.RedisManager
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor

@Controller
class ChatController(
    private val chatService: ChatService,
    private val redisManager: RedisManager
) {
    private val logger = LoggerFactory.getLogger(ChatController::class.java)

    @MessageMapping("/sendChat")
    fun regChat(@Payload(required = true) chatDTO: ChatDTO, accessor : SimpMessageHeaderAccessor) {
        chatService.publishChat(chatDTO, accessor)
    }
}
