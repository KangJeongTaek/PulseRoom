package com.kjt.PulseRoom.common.scheduler.task

import com.kjt.PulseRoom.chat.service.ChatService
import com.kjt.PulseRoom.common.redis.manager.RedisManager
import com.kjt.PulseRoom.service.PostgresService
import com.kjt.PulseRoom.service.RedisService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class DayScheduler(
    private val redisService: RedisService,
    private val postgresService: PostgresService,
    private val chatService: ChatService,
    private val redisManager: RedisManager
) {

    private val logger = LoggerFactory.getLogger(DayScheduler::class.java)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Scheduled(cron = "0 0 0 * * *")
    fun runDailyTask(){
        val todayVisitCount :Long= redisService.getTodayVisitCount()?.toLong() ?: 0
        logger.info("⚠ 오늘(${LocalDate.now()})의 방문자수 : ${todayVisitCount}")

        val comments = redisService.getAllComment()


        val chats = chatService.getChatHistory()
        redisManager.flushAll()
        logger.info("🗑 Redis 데이터 초기화 완료")

        val yesterdayCount = postgresService.yesterdayVisitCount()
        redisService.setYesterdayVisitCount(yesterdayCount)
        logger.info("📌 어제 방문자 수 저장 완료: $yesterdayCount")

        val visitCount = postgresService.allVisitCount()
        redisService.setAllVisitCount(visitCount)
        logger.info("✅ 전체 방문자 수 업데이트 완료: $visitCount")

        postgresService.saveAllComment(comments)
        logger.info("✅ 전체 글 업데이트 완료 : ${comments.size}")

        chatService.saveAllChats(chats)
        logger.info("✅ 전체 채팅 저장 완료 : ${chats.size}")

    }



}