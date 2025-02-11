package com.kjt.PulseRoom.controller

import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController(
    private val redisTemplate : RedisTemplate<String, String>,
    private val redisConnectionFactory : RedisConnectionFactory
) {
    private val logger = LoggerFactory.getLogger(RedisController::class.java)

    @GetMapping("/visit")
    fun test() : ResponseEntity<Any> {
        val visitCount : Long = redisTemplate.opsForValue().increment("today:visit") ?: 0
        return ResponseEntity.ok(mapOf("visitCount" to visitCount))
    }
}