package com.kjt.PulseRoom.service

import com.kjt.PulseRoom.comment.model.Comment
import com.kjt.PulseRoom.visit.model.Visit
import com.kjt.PulseRoom.comment.repository.CommentRepository
import com.kjt.PulseRoom.visit.repository.VisitRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class PostgresService(
    private val visitRepository: VisitRepository,
    private val commentRepository: CommentRepository
) {

    fun saveVisit(visit : Visit){
        visitRepository.save(visit)
    }

    fun saveAllComment(comments:List<Comment>){
        commentRepository.saveAll(comments)
    }

    fun yesterdayVisitCount() : Long{
        val yesterday = LocalDate.now().minusDays(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val day= formatter.format(yesterday)
        return visitRepository.countByDate(day)
    }

    fun allVisitCount() :Long{
        return visitRepository.count()
    }


}