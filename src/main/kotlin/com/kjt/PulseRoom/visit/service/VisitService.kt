package com.kjt.PulseRoom.visit.service

import com.kjt.PulseRoom.common.util.Util.Companion.createRandomName
import com.kjt.PulseRoom.visit.model.Visit
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class VisitService(
    private val visitRedisService: VisitRedisService,
    private val visitPostgresService: VisitPostgresService,
) {

    fun visit(hostIp: String?) {
        if(hostIp.isNullOrBlank()) throw IllegalArgumentException("잘못된 요청입니다.")
        val key = "today:visit:user:${hostIp}"

        //첫 방문 시
        if(isFirstVisit(key)){
            // redis에 저장
            visitRedisService.increaseTodayVisitCount()
            val nickname = createRandomName(hostIp)
            visitRedisService.putUserNickname(key,nickname)

            // postgresSQL에 저장
            visitPostgresService.saveVisit(Visit(hostIp = hostIp, nickname = nickname))
        }else{
            visitRedisService.increaseUserVisitCount(key)
        }
    }


    private fun isFirstVisit(hostIp:String) : Boolean{
        return visitRedisService.isFirstVisit(hostIp)
    }

    fun getVisitCount() : String{
        return visitRedisService.getAllVisitCount()
    }

    fun getDailyVisitCount() : String{
        return visitRedisService.getDailyVisitCount()
    }

    fun setYesterdayVisitCount(count : Long){
        visitRedisService.setYesterdayVisitCount(count)
    }

    fun setAllVisitCount(count : Long){
        visitRedisService.setAllVisitCount(count)
    }

    fun getYesterdayVisitCount() : Long{
        val yesterday = LocalDate.now().minusDays(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val day= formatter.format(yesterday)
        return visitPostgresService.getVisitCountByDate(day)
    }

    fun getAllVisitCount() : Long{
        return visitPostgresService.getAllVisitCount()
    }

}