package com.kjt.PulseRoom.scheduler

import com.kjt.PulseRoom.chat.service.ChatService
import com.kjt.PulseRoom.chat.service.ChatPostgresService
import com.kjt.PulseRoom.common.redis.manager.RedisManager
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class DaySchedulerTest(

){

 @Autowired
 private lateinit var chatPostgresService: ChatPostgresService

 @Autowired
 private lateinit var  chatService: ChatService

 @Autowired
 private lateinit var redisManager: RedisManager

 @Test
  fun pulseSchedulerTest(){
  }
 }