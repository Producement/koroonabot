package com.producement.koroonabot.dataprovider

import com.fasterxml.jackson.core.JsonFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonFactoryConfiguration {

  @Bean
  fun jsonFactory() = JsonFactory()
}
