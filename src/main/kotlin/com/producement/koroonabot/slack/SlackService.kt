package com.producement.koroonabot.slack

import com.kreait.slack.api.SlackClient
import com.kreait.slack.api.contract.jackson.group.chat.PostMessageRequest
import com.producement.koroonabot.slack.team.TeamRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class SlackService(
  private val slackClient: SlackClient,
  private val teamRepository: TeamRepository
) {

  fun sendAll(message: String) {
    val teams = teamRepository.findAll()
    for (team in teams) {
      val result = slackClient.chat()
        .postMessage(team.bot.accessToken)
        .with(PostMessageRequest(text = message, channel = team.incomingWebhook!!.channel))
        .invoke()
      if (result.wasFailure()) {
        log.error("Error sending to Slack: ${result.failure}")
      } else {
        log.info("Response from Slack: ${result.success}")
      }
    }
  }
}
