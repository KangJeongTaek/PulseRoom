package com.kjt.PulseRoom.comment.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    val commentNo : Long ? = null,

    @Column(name = "host_ip", nullable = false, length = 100)
    val comment : String,

    @Column(name = "crtDt", length = 20)
    val crtDt : LocalDateTime
) {
}