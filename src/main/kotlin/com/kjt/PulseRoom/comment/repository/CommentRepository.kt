package com.kjt.PulseRoom.comment.repository

import com.kjt.PulseRoom.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment,Long>{
}