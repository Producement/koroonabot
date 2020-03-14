package com.producement.koroonabot

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.Profiles

class DbContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

  override fun initialize(applicationContext: ConfigurableApplicationContext) {
    if (!applicationContext.environment.acceptsProfiles(Profiles.of("ci"))) {
      postgres.start()
      TestPropertyValues.of(
        "spring.datasource.url=${postgres.jdbcUrl}",
        "spring.datasource.username=${postgres.username}",
        "spring.datasource.password=${postgres.password}"
      ).applyTo(applicationContext.environment)
    }
  }

  companion object {
    private val postgres: KPostgreSQLContainer by lazy {
      KPostgreSQLContainer("postgres:alpine")
        .withDatabaseName("koroonabot")
        .withUsername("koroonabot")
        .withPassword("koroonabot")
    }
  }
}
