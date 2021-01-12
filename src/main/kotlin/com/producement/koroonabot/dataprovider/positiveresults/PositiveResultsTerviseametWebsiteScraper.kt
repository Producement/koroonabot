package com.producement.koroonabot.dataprovider.positiveresults

import com.producement.koroonabot.dataprovider.DataProvider
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
@Deprecated("Use StreamingJsonClient")
class PositiveResultsTerviseametWebsiteScraper : DataProvider {

  override fun getLatest(): Int {
    val positiveTests = Jsoup.connect("https://www.terviseamet.ee/et/uuskoroonaviirus").get()
      .select("strong:contains(POSITIIVSEID TESTE)").parents()[2].text().toLowerCase().capitalize()
      .filter { it.isDigit() }
    return positiveTests.toInt()
  }
}
