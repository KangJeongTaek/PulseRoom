package com.kjt.PulseRoom.dailyMessage.controller

import com.kjt.PulseRoom.dailyMessage.dto.DailyMessageDTO
import com.kjt.PulseRoom.dailyMessage.service.DailyMessageService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class DailyMessageController(
    private val dailyMessageService: DailyMessageService
) {

    @PostMapping("/dailyMessage")
    fun post(@RequestBody(required = true) dailyMessageDTO: DailyMessageDTO) : ResponseEntity<Any> {

            dailyMessageService.addDailyMessage(dailyMessageDTO)

            return ResponseEntity.ok(mapOf("result" to "성공", "msg" to "오늘도 화이팅이야!"))
    }
}
