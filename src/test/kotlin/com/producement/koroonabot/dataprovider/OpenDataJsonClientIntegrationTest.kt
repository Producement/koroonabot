package com.producement.koroonabot.dataprovider

import com.producement.koroonabot.dataprovider.OpenDataJsonClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
@Disabled
class OpenDataJsonClientIntegrationTest(@Autowired private val openDataJsonClient: OpenDataJsonClient) {

  @Test
  fun getLatestData() {
    val latestData = openDataJsonClient.getLatestData()
    assertThat(latestData).isEqualTo("Positiivseid teste 2003")
  }
}
