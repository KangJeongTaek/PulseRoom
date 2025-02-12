package com.kjt.PulseRoom.service

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisServerCommands
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

@Service
class RedisService(
    private val redisTemplate : RedisTemplate<String, String>,
    private val redisConnectionFactory : RedisConnectionFactory
) {
    private val logger = LoggerFactory.getLogger(RedisService::class.java)

    fun todayFirstVisit(hostIp : String?) : Boolean{
        if(hostIp.isNullOrBlank()){
            throw IllegalArgumentException("잘못된 요청입니다.")
        }
        val absent = redisTemplate.opsForValue().setIfAbsent("today:visit:user:${hostIp}","OK", Duration.ofDays(1))
        if(absent == true){
            redisTemplate.opsForValue().increment("today:visit:count")

            redisTemplate.opsForValue().set("today:visit:${hostIp}:name",createRandomName(hostIp))
        }
        return absent == true
    }

    fun getTodayVisitCount(): String?{
        return redisTemplate.opsForValue().get("today:visit:count")
    }

    fun setYesterdayVisitCount(count : Long){
        redisTemplate.opsForValue().set(
            "yesterday:visit:count",
            count.toString(),
            Duration.ofDays(1))
    }

    fun setAllVisitCount(count : Long){
        redisTemplate.opsForValue().set(
            "visit:count",
            count.toString(),
            Duration.ofDays(1)
        )
    }

    fun getVisitCount(): String?{
        return redisTemplate.opsForValue().get("today:visit:count")
    }

    fun getUser(hostIp: String?) :Map<String,String>{
        val nickName = redisTemplate.opsForValue().get("today:visit:${hostIp}:name") ?: throw NoSuchElementException("해당 하는 유저가 없습니다.")

        return mapOf("nickname" to nickName)
    }

    fun flushAll(){
        redisTemplate.connectionFactory?.
        connection?.
        serverCommands()?.
        flushAll(RedisServerCommands.FlushOption.ASYNC)
    }


    private fun createRandomName(hostIp : String?) : String{
        val adjectives = listOf(
            "귀여운", "활기찬", "신비한", "따뜻한", "밝은", "고요한", "순수한", "강력한", "화려한", "유쾌한",
            "우아한", "신나는", "용감한", "지혜로운", "든든한", "산뜻한", "달콤한", "푸른", "빛나는", "아름다운",
            "활발한", "차가운", "깨끗한", "느긋한", "신속한", "즐거운", "순박한", "기운찬", "평온한", "부드러운",
            "튼튼한", "상큼한", "시원한", "정직한", "용의주도한", "소박한", "포근한", "독창적인", "강한", "차분한",
            "날렵한", "온화한", "화끈한", "기발한", "유려한", "짜릿한", "차분한", "유명한", "당당한", "똑똑한",
            "온순한", "무뚝뚝한", "선명한", "깔끔한", "의젓한", "기이한", "호기심 많은", "잔잔한", "단단한", "소중한",
            "싱그러운", "눈부신", "적극적인", "섬세한", "느긋한", "활발한", "청량한", "단아한", "가벼운", "꿈같은",
            "몽환적인", "정겨운", "푸근한", "안락한", "강렬한", "익살맞은", "선한", "친절한", "고풍스러운", "아늑한",
            "예리한", "순진한", "다정한", "신비로운", "따사로운", "힘찬", "낭만적인", "풍요로운", "능숙한", "섹시한",
            "따뜻한", "호탕한", "넉넉한", "담대한", "경쾌한", "유머러스한", "활달한", "우직한", "비범한", "특별한"
        )
        val nouns = listOf(
            "바람결", "햇살", "달빛", "별빛", "하늘", "푸른솔", "나래", "미르", "노을", "바람꽃",
            "산들바람", "가온누리", "별하늘", "새벽", "초롱별", "은하수", "바다", "이슬", "꽃비", "구름",
            "달맞이", "솔바람", "시냇물", "푸른강", "고운빛", "여울", "소나기", "푸른섬", "달무리", "한울",
            "무지개", "호수", "고요", "별그림", "아침햇살", "산새", "별하늘", "솔향기", "푸른들", "노래",
            "하늘꿈", "달그림자", "푸른달", "꽃샘", "새솔", "아라", "마루", "이든", "다소니", "라온",


            "호랑이", "토끼", "고래", "사자", "독수리", "거북이", "용", "펭귄", "부엉이", "여우",
            "코알라", "강아지", "고양이", "곰", "수달", "캥거루", "다람쥐", "늑대", "치타", "코끼리",
            "앵무새", "까치", "비둘기", "문어", "오징어", "고슴도치", "돌고래", "나비", "벌", "사슴",
            "판다", "기린", "타조", "하이에나", "두더지", "참새", "낙타", "코브라", "뱀", "노루"
        )

        val today = LocalDate.now().toEpochDay()
        val randomFactor = UUID.randomUUID().mostSignificantBits
        val seed = hostIp.hashCode().toLong() xor today xor randomFactor
        val random = Random(seed)

        val adjective = adjectives[random.nextInt(adjectives.size)]
        val noun = nouns[random.nextInt(nouns.size)]

        return "$adjective $noun"
    }
}