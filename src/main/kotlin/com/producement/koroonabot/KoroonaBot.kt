package com.producement.koroonabot

import com.producement.koroonabot.message.Message
import com.producement.koroonabot.message.MessageRepository
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class KoroonaBot(
  private val slackService: SlackService,
  private val messageRepository: MessageRepository,
  private val terviseametWebsiteScraper: TerviseametWebsiteScraper
) {

  @Scheduled(cron = "0 * * * * *")
  fun poller() {
    val latestData = terviseametWebsiteScraper.getLatestData()
    log.info("Latest data: $latestData")
    val latestSentMessage = messageRepository.findTopByOrderByIdDesc().message
    if(latestData != latestSentMessage) {
      log.info("Sending to Slack: $latestData")
      messageRepository.save(Message(message = latestData))
      slackService.sendAll(latestData)
    }
  }
}
