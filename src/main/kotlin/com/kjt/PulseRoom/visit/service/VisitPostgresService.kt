package com.kjt.PulseRoom.visit.service

import com.kjt.PulseRoom.visit.model.Visit
import com.kjt.PulseRoom.visit.repository.VisitRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class VisitPostgresService(
    private val visitRepository: VisitRepository
) {

    fun saveVisit(visit : Visit){
        visitRepository.save(visit)
    }

    fun getVisitCountByDate(day : String) : Long{
        return visitRepository.countByDate(day)
    }

    fun getAllVisitCount(): Long{
        return visitRepository.count()
    }
}