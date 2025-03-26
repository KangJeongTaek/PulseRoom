package com.kjt.PulseRoom.user.service

import com.kjt.PulseRoom.common.redis.manager.RedisManager
import com.kjt.PulseRoom.user.dto.UserDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserRedisService(
    private val redisManager: RedisManager
) {
    private val logger = LoggerFactory.getLogger(UserRedisService::class.java)


    fun getUser(hostIp : String) : UserDTO{
        val key = "today:visit:user:${hostIp}"
        val userEntries = redisManager.hashGetAll(key)

        if(userEntries.isEmpty()) throw NoSuchElementException("해당하는 유저가 없습니다.")

        val nickname = userEntries["nickname"].toString()
        val hits = userEntries["hits"].toString()

        return UserDTO(nickname,hits)
    }
}