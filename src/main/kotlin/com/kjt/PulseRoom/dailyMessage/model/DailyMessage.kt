package com.kjt.PulseRoom.dailyMessage.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "daily_message")
class DailyMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_message_no", nullable = false)
    val dailyMessageNo : Long ? = null,

    @Column(name = "content", nullable = false, length = 100)
    val content : String,

    @Column(name = "crtDt", length = 20)
    val crtDt : LocalDateTime ?= LocalDateTime.now()
) {
}