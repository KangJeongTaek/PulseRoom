package com.kjt.PulseRoom.controller

import com.kjt.PulseRoom.model.Visit
import com.kjt.PulseRoom.service.PostgresService
import com.kjt.PulseRoom.service.RedisService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VisitController(
    private val redisService: RedisService,
    private val postgresService: PostgresService
) {
    private val logger = LoggerFactory.getLogger(VisitController::class.java)

    @GetMapping("/visit")
    fun test(request: HttpServletRequest) : ResponseEntity<Any> {

        val firstVisit = redisService.todayFirstVisit(request.remoteAddr)
        val nickname = redisService.getUser(request.remoteAddr)["nickname"]

        if(firstVisit){
            postgresService.saveVisit(visit = Visit(hostIp = request.remoteAddr, nickname = redisService.getUser(request.remoteAddr)["nickname"]))
        }

        val message = if(firstVisit) "first" else "not"

        val visitCount = redisService.getVisitCount()
        return ResponseEntity.ok(mapOf("nickname" to nickname, "message" to message, "visitCount" to visitCount))
    }
}