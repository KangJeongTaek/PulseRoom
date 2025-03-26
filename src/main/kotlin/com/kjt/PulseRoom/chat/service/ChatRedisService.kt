package com.kjt.PulseRoom.chat.service

import com.kjt.PulseRoom.chat.dto.ChatDTO
import com.kjt.PulseRoom.common.redis.manager.RedisManager
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class ChatRedisService(
    private val redisManager: RedisManager
) {

    // 채팅을 publish 하는 메서드
    fun publish(chat : ChatDTO){
        redisManager.addToStream("chat", mapOf(
            "hostIp" to chat.hostIp!!,
            "content" to chat.content
        ))
    }

    // 지금까지의 채팅 이력을 가져오는 메서드
    fun getChatHistory() :List<ChatDTO>{
        val messages = redisManager.readFromStream("chat")

        return messages?.map {
            message -> message.value
            val hostIp = message.value["hostIp"]?.toString() ?: "unknown"
            val content = message.value["content"]?.toString() ?: "unknown"
            val timestamp = message.id.timestamp
            val crtDt = LocalDateTime.ofInstant(timestamp?.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault())
            ChatDTO(hostIp = hostIp, content = content, crtDt = crtDt)
        } ?: emptyList()
    }
}