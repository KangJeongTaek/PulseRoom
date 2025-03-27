package com.kjt.PulseRoom.admin.controller

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {

    private val logger : Logger = LoggerFactory.getLogger(AdminController::class.java)

    @GetMapping("/admin")
    fun admin(httpServletRequest: HttpServletRequest) : String{
        logger.info("관리 페이지 접속 IP : ${httpServletRequest.remoteAddr}")
        return "/admin"
    }
}