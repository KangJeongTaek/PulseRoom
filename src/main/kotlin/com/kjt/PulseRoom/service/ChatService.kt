package com.kjt.PulseRoom.service

import com.kjt.PulseRoom.model.Chat
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.connection.stream.StreamReadOptions
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class ChatService(
    private val redisTemplate : RedisTemplate<String, String>,
    private val redisConnectionFactory : RedisConnectionFactory,
    private val streamMessageListenerContainer: StreamMessageListenerContainer<String, MapRecord<String, String, String>>,
    private val simpleMessageSendingOperations : SimpMessageSendingOperations

) {
    private val logger = LoggerFactory.getLogger(ChatService::class.java)

    private var lastMessageId : String = "0"
    fun pubChat(chat : Chat){
        redisTemplate.opsForStream<Any,Any>().add("chat", mapOf(
            "hostIp" to chat.hostIp,
            "content" to chat.content
        ))
    }

    fun getChatHistory() :List<Chat>{
        val messages = redisTemplate.opsForStream<Any,Any>().read(
            StreamReadOptions.empty(),StreamOffset.fromStart("chat")
        )
        return messages?.map { message ->
            val hostIp = message.value["hostIp"]?.toString() ?: "unknown"
            val content = message.value["content"]?.toString() ?: "unknown"
            val timestamp = message.id.timestamp
            val crtDt = LocalDateTime.ofInstant(timestamp?.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault())


            Chat(hostIp = hostIp, content = content, crtDt = crtDt)
        } ?: emptyList()
    }
}