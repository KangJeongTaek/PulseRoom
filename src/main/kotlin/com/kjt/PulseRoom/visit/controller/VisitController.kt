package com.kjt.PulseRoom.visit.controller

import com.kjt.PulseRoom.chat.service.ChatService
import com.kjt.PulseRoom.dailyMessage.service.DailyMessageService
import com.kjt.PulseRoom.user.service.UserService
import com.kjt.PulseRoom.visit.service.VisitService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VisitController(
    private val chatService: ChatService,
    private val userService: UserService,
    private val visitService: VisitService,
    private val dailyMessageService: DailyMessageService
) {
    private val logger = LoggerFactory.getLogger(VisitController::class.java)

    @GetMapping("/visit")
    fun visit(request: HttpServletRequest) : ResponseEntity<Any> {
        val hostIp = request.remoteAddr

        visitService.visit(hostIp)

        val user = userService.getUser(hostIp)

        val nickname = user.nickname
        val hits = user.hits


        val visitCount = visitService.getVisitCount()
        val todayComment = dailyMessageService.getLastMessage()
        val todayChat = chatService.getChatHistory()
        return ResponseEntity.ok(mapOf("nickname" to nickname,"visitCount" to visitCount, "hits" to hits, "todayComment" to todayComment, "todayChat" to todayChat))
    }
}