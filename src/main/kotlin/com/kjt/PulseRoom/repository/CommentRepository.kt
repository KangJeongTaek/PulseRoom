package com.kjt.PulseRoom.repository

import com.kjt.PulseRoom.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment,Long>{
}