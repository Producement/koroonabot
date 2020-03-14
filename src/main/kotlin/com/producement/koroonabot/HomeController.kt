package com.producement.koroonabot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
  private val slackService: SlackService,
    private val terviseametWebsiteScraper: TerviseametWebsiteScraper
) {

  @GetMapping("/")
  fun home(): String {
    val latestData = terviseametWebsiteScraper.getLatestData()
    slackService.sendAll(latestData)
    return "KoroonaBot installeeritud."
  }
}
