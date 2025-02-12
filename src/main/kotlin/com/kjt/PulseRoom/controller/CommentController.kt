package com.kjt.PulseRoom.controller

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/comment")
class CommentController {

    private val logger : Logger = LoggerFactory.getLogger(CommentController::class.java)

    @GetMapping
    fun comment(httpServletRequest: HttpServletRequest) : String{
        logger.info("접속 IP : ${httpServletRequest.remoteAddr}")
        return "/comment"
    }

    @PostMapping("/edit")
    fun edit(@RequestParam("comment") comment : String) :ResponseEntity<Any>{
        logger.info("comment : $comment")
        return ResponseEntity.ok("성공")
    }
}