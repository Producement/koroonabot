package com.producement.koroonabot

import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class TerviseametWebsiteScraper {

  fun getLatestData(): String {
    return Jsoup.connect("https://www.terviseamet.ee/et/uuskoroonaviirus").get()
          .select("li strong:contains(Kokku on Eestis koroonaviirus)")[0].text()
  }
}
