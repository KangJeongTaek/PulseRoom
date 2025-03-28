package com.kjt.PulseRoom.visit.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "visit")
class Visit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_no", nullable = false)
    val visitNo : Long ? = null,

    @Column(name = "host_ip", nullable = false, length = 40)
    val hostIp : String,

    @Column(name = "crt_dt")
    val crtDt :Instant? = Instant.now(),

    @Column(name = "nickname", length = 20)
    val nickname : String?

) {


}