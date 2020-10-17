package com.producement.koroonabot.slack

import com.kreait.slack.broker.autoconfiguration.credentials.CredentialsProvider
import com.kreait.slack.broker.autoconfiguration.credentials.EnvironmentVariableCredentialsProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackConfiguration {

  @Bean
  fun slackCredentialsProvider(): CredentialsProvider {
    return EnvironmentVariableCredentialsProvider()
  }
}
