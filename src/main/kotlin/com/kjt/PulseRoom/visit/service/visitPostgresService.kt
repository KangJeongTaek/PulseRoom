package com.kjt.PulseRoom.visit.service

import com.kjt.PulseRoom.visit.model.Visit
import com.kjt.PulseRoom.visit.repository.VisitRepository
import org.springframework.stereotype.Service

@Service
class visitPostgresService(
    private val visitRepository: VisitRepository
) {

    fun saveVisit(visit : Visit){
        visitRepository.save(visit)
    }
}