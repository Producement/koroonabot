package com.producement.koroonabot.dataprovider.positiveresults

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class PositiveResultsTerviseametWebsiteScraperIntegrationTest {

  val terviseametScraper = PositiveResultsTerviseametWebsiteScraper()

  @Test
  fun getLatestData() {
    val latestData = terviseametScraper.getLatest()
    assertThat(latestData).isEqualTo(115)
  }
}
