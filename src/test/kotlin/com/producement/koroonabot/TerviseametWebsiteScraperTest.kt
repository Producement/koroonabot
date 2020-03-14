package com.producement.koroonabot

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class TerviseametWebsiteScraperTest {

  val terviseametScraper = TerviseametWebsiteScraper()

  @Test
  @Disabled
  fun getLatestData() {
    val latestData = terviseametScraper.getLatestData()
    assertThat(latestData).isEqualTo("Kokku on Eestis koroonaviirusesse nakatunud 115 inimest.")
  }
}
