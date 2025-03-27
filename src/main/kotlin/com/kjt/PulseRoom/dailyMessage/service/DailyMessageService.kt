package com.kjt.PulseRoom.dailyMessage.service

import com.kjt.PulseRoom.common.redis.manager.RedisManager
import com.kjt.PulseRoom.dailyMessage.dto.DailyMessageDTO
import org.springframework.stereotype.Service

@Service
class DailyMessageService(
    private val dailyMessageRedisService: DailyMessageRedisService,
    private val dailyMessagePostgresService: DailyMessagePostgresService,
) {


    fun addDailyMessage(dailyMessageDTO : DailyMessageDTO){
        val content = dailyMessageDTO.content
        if(content.isBlank()) throw IllegalArgumentException("빈 값을 넣지 마시오.")
        dailyMessageRedisService.addDailyMessage(content)
    }

    fun getLastMessage() : String{
        return dailyMessageRedisService.getLastMessage()
    }

    fun getDailyMessages() : List<DailyMessageDTO>{
        return dailyMessageRedisService.getDailyMessages()
    }

    fun saveAllDailyMessages(dailyMessages: List<DailyMessageDTO>){
        dailyMessagePostgresService.saveAllDailyMessages(dailyMessages)
    }



//

}