package com.producement.koroonabot

import com.producement.koroonabot.dataprovider.DataProvider
import com.producement.koroonabot.slack.SlackService
import com.producement.koroonabot.slack.message.Message
import com.producement.koroonabot.slack.message.MessageRepository
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class KoroonaBot(
  private val slackService: SlackService,
  private val messageRepository: MessageRepository,
  private val streamingJsonClient: DataProvider
) {

  @Scheduled(cron = "0 * * * * *")
  fun poll() {
    val latestPositiveTests = streamingJsonClient.getLatestPositiveTests()
    val latestData = "Positiivseid teste $latestPositiveTests"
    log.info("Latest data: $latestData")
    val lastSentMessage = messageRepository.findTopByOrderByIdDesc().message
    val lastSentNumber = lastSentMessage.filter { it.isDigit() }.toInt()
    if (latestData != lastSentMessage && latestPositiveTests > lastSentNumber) {
      log.info("Sending to Slack: $latestData")
      messageRepository.save(Message(message = latestData))
      slackService.sendAll(latestData)
    }
  }
}
