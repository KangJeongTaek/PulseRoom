package com.kjt.PulseRoom.scheduler

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class RedisScheduler {

    private val logger = LoggerFactory.getLogger(RedisScheduler::class.java)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

//    @Scheduled(cron = "*/1 * * * * ?")
//    fun runCronTask(){
//        logger.info("⏳ [Cron] 매 분마다 실행되는 작업: ${LocalDateTime.now().format(formatter)}")
//    }
}