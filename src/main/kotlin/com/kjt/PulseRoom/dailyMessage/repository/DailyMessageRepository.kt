package com.kjt.PulseRoom.dailyMessage.repository

import com.kjt.PulseRoom.dailyMessage.model.DailyMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DailyMessageRepository : JpaRepository<DailyMessage,Long>{

}