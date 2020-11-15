package com.producement.koroonabot.dataprovider

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class TerviseametWebsiteScraperIntegrationTest {

  val terviseametScraper = TerviseametWebsiteScraper()

  @Test
  fun getLatestData() {
    val latestData = terviseametScraper.getLatestPositiveTests()
    assertThat(latestData).isEqualTo(115)
  }
}
