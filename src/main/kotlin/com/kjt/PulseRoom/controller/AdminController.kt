package com.kjt.PulseRoom.controller

import com.kjt.PulseRoom.service.PostgresService
import com.kjt.PulseRoom.service.RedisService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class CommentController(
    private val redisService: RedisService,
    private val postgresService: PostgresService
) {

    private val logger : Logger = LoggerFactory.getLogger(CommentController::class.java)

    @GetMapping("/admin")
    fun admin(httpServletRequest: HttpServletRequest) : String{
        logger.info("관리 페이지 접속 IP : ${httpServletRequest.remoteAddr}")
        return "/admin"
    }

    @PostMapping("/comment/edit")
    fun edit(@RequestBody(required = true) commentDTO: CommentDTO) :ResponseEntity<Any>{
        val comment = commentDTO.comment
        if(comment.isBlank()) throw IllegalArgumentException("빈 값을 넣지 마시오.")
        redisService.addComment(commentDTO.comment)

        return ResponseEntity.ok(mapOf("result" to "성공", "msg" to "오늘도 화이팅이야!"))
    }
}

data class CommentDTO(
    val comment : String
)