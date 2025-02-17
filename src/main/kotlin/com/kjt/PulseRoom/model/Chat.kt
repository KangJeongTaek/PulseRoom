package com.kjt.PulseRoom.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table
@Entity(name = "chat")
class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_no", nullable = false)
    val chatNo : Long? = null,

    @Column(name = "writer_ip", nullable = false, length = 40)
    val hostIp : String,

    @Column(name = "content", nullable = false, length = 100)
    val content : String,

    @Column(name = "crtDt", length = 20)
    val crtDt : LocalDateTime ?= null


) {


}