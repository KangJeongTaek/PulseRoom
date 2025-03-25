package com.kjt.PulseRoom.common.redis

import org.springframework.data.redis.connection.RedisServerCommands
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.connection.stream.StreamReadOptions
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisManager(
    private val redisTemplate: RedisTemplate<String, Any>,
) {

    // 특정 스트림에 추가
    fun addToStream(streamKey : String, data:Map<String,String>){
        redisTemplate.opsForStream<Any,Any>().add(streamKey,data)
    }

    // 특정 스트림의 지금까지의 값을 읽어 오기
    fun readFromStream(streamKey : String) : MutableList<MapRecord<String, Any, Any>>? {
        val messages = redisTemplate.opsForStream<Any,Any>().read(
            StreamReadOptions.empty(), StreamOffset.fromStart(streamKey)
        )
        return messages
    }

    // 모든 데이터 제거
    fun flushAll(){
        redisTemplate.connectionFactory?.
        connection?.
        serverCommands()?.
        flushAll(RedisServerCommands.FlushOption.ASYNC)
    }

}