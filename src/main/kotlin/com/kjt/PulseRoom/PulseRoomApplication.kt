package com.kjt.PulseRoom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PulseRoomApplication

fun main(args: Array<String>) {
	runApplication<PulseRoomApplication>(*args)
}
