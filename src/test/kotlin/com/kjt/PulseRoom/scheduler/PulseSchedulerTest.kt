package com.kjt.PulseRoom.scheduler

import com.kjt.PulseRoom.chat.service.ChatService
import com.kjt.PulseRoom.chat.service.PostgresChatService
import com.kjt.PulseRoom.common.redis.RedisManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class PulseSchedulerTest(

){

 @Autowired
 private lateinit var postgresChatService: PostgresChatService

 @Autowired
 private lateinit var  chatService: ChatService

 @Autowired
 private lateinit var redisManager: RedisManager

 @Test
  fun pulseSchedulerTest(){
  }
 }