package com.producement.koroonabot.dataprovider

import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
@Deprecated("Use StreamingJsonClient")
class TerviseametWebsiteScraper : DataProvider {

  override fun getLatestPositiveTests(): Int {
    val positiveTests = Jsoup.connect("https://www.terviseamet.ee/et/uuskoroonaviirus").get()
      .select("strong:contains(POSITIIVSEID TESTE)").parents()[2].text().toLowerCase().capitalize()
      .filter { it.isDigit() }
    return positiveTests.toInt()
  }
}
