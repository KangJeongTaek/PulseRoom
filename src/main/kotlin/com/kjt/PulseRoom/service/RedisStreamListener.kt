package com.kjt.PulseRoom.service;

import com.kjt.PulseRoom.model.Chat
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class RedisStreamListener (
    private val simpleMessageSendingOperations:SimpMessageSendingOperations
) : StreamListener<String, MapRecord<String, String, String>>{
    private val logger = LoggerFactory.getLogger(RedisStreamListener::class.java)

    override fun onMessage(message: MapRecord<String, String, String>?) {
        val hostIp = message?.value?.get("hostIp")?.toString() ?: "unknown"
        val content = message?.value?.get("content")?.toString() ?: "unknown"
        val timestamp = message?.id?.timestamp
        val crtDt = LocalDateTime.ofInstant(timestamp?.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault())
        logger.info("새로운 메시지 수신 : ${content}")

        simpleMessageSendingOperations.convertAndSend("/topic/public", Chat(hostIp = hostIp, content = content, crtDt = crtDt))
    }
}
