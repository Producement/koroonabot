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
  private val positiveResultsStreamingJsonClient: DataProvider,
  private val vaccinationsStreamingJsonClient: DataProvider
) {

  @Scheduled(cron = "0 * * * * *")
  fun pollLatestData() {
    positiveTests()
    vaccinations()
  }

  fun positiveTests() {
    sendToSlack("Positiivseid teste", positiveResultsStreamingJsonClient.getLatest())
  }

  fun vaccinations() {
    sendToSlack("Vaktsineerimisi", vaccinationsStreamingJsonClient.getLatest())
  }

  private fun sendToSlack(prefix: String, number: Int) {
    val latestData = "$prefix $number"
    log.info("Latest data: $latestData")
    val lastSentMessage = messageRepository.findTopByMessageContainingOrderByIdDesc(prefix)?.message ?: "0"
    val lastSentNumber = lastSentMessage.filter { it.isDigit() }.toInt()
    if (latestData != lastSentMessage && number > lastSentNumber) {
      log.info("Sending to Slack: $latestData")
      messageRepository.save(Message(message = latestData))
      slackService.sendAll(latestData)
    }
  }
}
