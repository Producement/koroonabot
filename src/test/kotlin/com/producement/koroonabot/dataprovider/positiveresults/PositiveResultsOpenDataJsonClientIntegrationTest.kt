package com.producement.koroonabot.dataprovider.positiveresults

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
@Disabled
class PositiveResultsOpenDataJsonClientIntegrationTest(
  @Autowired private val openDataJsonClient: PositiveResultsOpenDataJsonClient
) {

  @Test
  fun getLatestData() {
    val latestPositiveTests = openDataJsonClient.getLatest()
    assertThat(latestPositiveTests).isEqualTo(2003)
  }
}
