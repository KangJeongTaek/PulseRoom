package com.kjt.PulseRoom.dailyMessage.service

import com.kjt.PulseRoom.dailyMessage.dto.DailyMessageDTO
import com.kjt.PulseRoom.dailyMessage.repository.DailyMessageRepository
import org.springframework.stereotype.Service

@Service
class DailyMessagePostgresService(
    private val dailyMessageRepository: DailyMessageRepository
) {

    fun saveAllDailyMessages(dailyMessages: List<DailyMessageDTO>){
        dailyMessageRepository.saveAll(dailyMessages.map {
            it.toModel()
        })
    }
}