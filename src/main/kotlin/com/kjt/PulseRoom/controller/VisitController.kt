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
        val user = redisService.getUser(request.remoteAddr)
        val nickname = user["nickname"]
        val hits = user["hits"]

        if(firstVisit){
            postgresService.saveVisit(visit = Visit(hostIp = request.remoteAddr, nickname = user["nickname"]))
        }


        val visitCount = redisService.getAllVisitCount() ?: 0
        val todayComment = redisService.getLastComment()?.comment ?: "아직 오늘의 한 마디가 등록되지 않았습니다."
        return ResponseEntity.ok(mapOf("nickname" to nickname,"visitCount" to visitCount, "hits" to hits, "todayComment" to todayComment))
    }
}