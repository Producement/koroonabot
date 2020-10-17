package com.producement.koroonabot.dataprovider

import com.producement.koroonabot.dataprovider.TerviseametWebsiteScraper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class TerviseametWebsiteScraperTest {

  val terviseametScraper = TerviseametWebsiteScraper()

  @Test
  @Disabled
  fun getLatestData() {
    val latestData = terviseametScraper.getLatestData()
    assertThat(latestData).isEqualTo("Kokku on Eestis koroonaviirus diagnoositud 115 inimesel.")
  }
}
