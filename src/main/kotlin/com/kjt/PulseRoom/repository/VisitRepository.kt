package com.kjt.PulseRoom.repository

import com.kjt.PulseRoom.model.Visit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VisitRepository : JpaRepository<Visit,Long> {

    @Query(value = "SELECT count(1) FROM visit WHERE crt_dt::DATE = TO_DATE(:day, 'YYYY-MM-DD')",
        nativeQuery = true)
    fun countByDate(@Param("day") day:String):Long

}