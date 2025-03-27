package com.kjt.PulseRoom.dailyMessage.service

import com.kjt.PulseRoom.common.redis.manager.RedisManager
import com.kjt.PulseRoom.dailyMessage.dto.DailyMessageDTO
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class DailyMessageRedisService(
    private val redisManager: RedisManager
) {

    fun addDailyMessage(content:String){
        val key = "today:comments"
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val timestamp = LocalDateTime.now().format(formatter).toDouble()

        redisManager.zsetAdd(key,content,timestamp)
    }

    fun getLastMessage() :String{
        val key = "today:comments"
        val messagesWithScore = redisManager.zsetRangeWithScores(key,-1,-1)

        if(messagesWithScore.isNullOrEmpty()) return "아직 오늘의 한 마디가 등록되지 않았습니다."

        val message :String = messagesWithScore.first().value.toString()
        return message
    }

    fun getDailyMessages() : List<DailyMessageDTO>{
        val key = "today:comments"
        val messagesWithScore = redisManager.zsetRangeWithScores(key,0,-1)

        if(messagesWithScore.isNullOrEmpty()) return emptyList()

        val dailyMessages = messagesWithScore.map {
            it ->
            val timestamp = it.score.toString()
            val epochMillis = timestamp.toDouble().toLong()
            val content = it.value.toString()

            val crtDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault())

            DailyMessageDTO(content = content, crtDt = crtDt)
        }
        return dailyMessages
    }
}