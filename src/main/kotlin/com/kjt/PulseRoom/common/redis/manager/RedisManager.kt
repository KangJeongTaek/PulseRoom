package com.kjt.PulseRoom.common.redis.manager

import org.springframework.boot.autoconfigure.data.redis.RedisConnectionDetails
import org.springframework.data.redis.connection.RedisServerCommands
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.connection.stream.StreamReadOptions
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisManager(
    private val redisTemplate: RedisTemplate<String, Any>,
) {

    // get null을 리턴할 수 있으므로 호출하는 곳에서 기본값을 설정해줄 것!
    fun get(key: String) : String? {
        return try{
            redisTemplate.opsForValue().get(key) as String
        }catch (e : NullPointerException) {
            null
        }
    }

    // set
    fun set(key: String, value: Any){
        redisTemplate.opsForValue().set(key, value)
    }

    fun set(key:String,value:Any,timeOut :Duration){
        redisTemplate.opsForValue().set(key,value,timeOut)
    }

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

    //해시 모든 필드 가져오기
    fun hashGetAll(key : String) : MutableMap<String,Any>{
        return redisTemplate.opsForHash<String,Any>().entries(key)
    }

    // 해시 put
    fun hashPut(key : String, hashKey : String,value : Any) {
        redisTemplate.opsForHash<String,Any>().put(key,hashKey,value)
    }

    // 해시 putIfAbsent 비어 있었다면 true, 이미 존재했다면 false
    fun hashPutIfAbsent(key : String, hashKey : String ,value : Any) : Boolean{
        return redisTemplate.opsForHash<String,Any>().putIfAbsent(key,hashKey,value)
    }

    // 해시 increase
    fun hashIncrement(key : String, hashKey : String, value : Long) :Long{
        return redisTemplate.opsForHash<String,Any>().increment(key,hashKey,value)
    }

    // increase
    fun increment(key : String) : Long?{
        return redisTemplate.opsForValue().increment(key)
    }

    // increase
    fun increment(key : String,delta:Long) : Long?{
        return redisTemplate.opsForValue().increment(key,delta)
    }

    // increase
    fun increment(key : String,delta:Double) : Double?{
        return redisTemplate.opsForValue().increment(key,delta)
    }

    // zsetAdd
    fun zsetAdd(key : String,value : Any, score : Double){
        redisTemplate.opsForZSet().add(key, value, score)
    }

    // zsetRangeWithScores
    fun zsetRangeWithScores(key : String,start:Long,end :Long): MutableSet<ZSetOperations.TypedTuple<Any>>? {
        return redisTemplate.opsForZSet().rangeWithScores(key,start,end)
    }

}