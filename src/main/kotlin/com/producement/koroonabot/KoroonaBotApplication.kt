package com.producement.koroonabot

import com.kreait.slack.broker.autoconfiguration.credentials.CredentialsProvider
import com.kreait.slack.broker.autoconfiguration.credentials.EnvironmentVariableCredentialsProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class KoroonaBotApplication {

  @Bean
  fun slackCredentialsProvider(): CredentialsProvider {
    return EnvironmentVariableCredentialsProvider()
  }
}

fun main(args: Array<String>) {
  runApplication<KoroonaBotApplication>(*args)
}

