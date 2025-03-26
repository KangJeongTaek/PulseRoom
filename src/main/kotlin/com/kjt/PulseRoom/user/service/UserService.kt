package com.kjt.PulseRoom.user.service

import com.kjt.PulseRoom.user.dto.UserDTO
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRedisService: UserRedisService
) {

    fun getUser(hostIp: String): UserDTO {
        return userRedisService.getUser(hostIp)
    }
}