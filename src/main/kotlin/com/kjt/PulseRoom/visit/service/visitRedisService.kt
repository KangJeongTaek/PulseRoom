package com.kjt.PulseRoom.visit.service

import com.kjt.PulseRoom.common.redis.manager.RedisManager
import org.springframework.stereotype.Service

@Service
class visitRedisService(
    private val redisManager: RedisManager
) {

    fun isFirstVisit(key:String) : Boolean{
        return redisManager.hashPutIfAbsent(key,"hits","1")
    }

    fun increaseTodayVisitCount(){
        redisManager.increment("today:visit:count")
    }

    fun putUserNickname(key:String,nickname:String){
        redisManager.hashPut(key,"nickname",nickname)
    }

    fun increaseUserVisitCount(key:String){
        redisManager.hashIncrement(key,"hits",1)
    }

    fun getAllVisitCount() : String{
        return redisManager.get("visit:count") ?: "0"
    }
}