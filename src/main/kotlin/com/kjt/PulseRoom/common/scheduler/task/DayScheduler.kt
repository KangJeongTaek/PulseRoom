package com.kjt.PulseRoom.common.scheduler.task

import com.kjt.PulseRoom.chat.service.ChatService
import com.kjt.PulseRoom.common.redis.manager.RedisManager
import com.kjt.PulseRoom.dailyMessage.service.DailyMessageService
import com.kjt.PulseRoom.visit.service.VisitService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DayScheduler(
    private val chatService: ChatService,
    private val redisManager: RedisManager,
    private val dailyMessageService: DailyMessageService,
    private val visitService: VisitService
) {

    private val logger = LoggerFactory.getLogger(DayScheduler::class.java)

    @Scheduled(cron = "0 0 0 * * *")
    fun runDailyTask(){

        val todayVisitCount :String= visitService.getDailyVisitCount()
        logger.info("⚠ 오늘(${LocalDate.now()})의 방문자수 : $todayVisitCount")

        try {
            val dailyMessages = dailyMessageService.getDailyMessages()



        val chats = chatService.getChatHistory()
        redisManager.flushAll()
        logger.info("🗑 Redis 데이터 초기화 완료")

        val yesterdayCount = visitService.getYesterdayVisitCount()
        visitService.setYesterdayVisitCount(yesterdayCount)
        logger.info("📌 어제 방문자 수 저장 완료: $yesterdayCount")

        val visitCount = visitService.getAllVisitCount()
        visitService.setAllVisitCount(visitCount)
        logger.info("✅ 전체 방문자 수 업데이트 완료: $visitCount")

        dailyMessageService.saveAllDailyMessages(dailyMessages)
        logger.info("✅ 전체 글 업데이트 완료 : ${dailyMessages.size}")

        chatService.saveAllChats(chats)
        logger.info("✅ 전체 채팅 저장 완료 : ${chats.size}")

        }catch (e : Exception){
            logger.error("스케쥴러 작동 중 오류 발생 !! ${e.stackTraceToString()}")
        }
    }



}