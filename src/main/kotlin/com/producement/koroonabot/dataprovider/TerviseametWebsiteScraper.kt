package com.producement.koroonabot.dataprovider

import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class TerviseametWebsiteScraper : DataProvider {

  override fun getLatestData(): String {
    return Jsoup.connect("https://www.terviseamet.ee/et/uuskoroonaviirus").get()
      .select("strong:contains(POSITIIVSEID TESTE)").parents()[2].text().toLowerCase().capitalize()
  }
}
