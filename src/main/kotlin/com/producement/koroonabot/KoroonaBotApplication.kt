package com.producement.koroonabot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class KoroonaBotApplication

fun main(args: Array<String>) {
	runApplication<KoroonaBotApplication>(*args)
}
