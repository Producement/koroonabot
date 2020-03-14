package com.producement.koroonabot

import com.kreait.slack.api.SlackClient
import com.kreait.slack.api.contract.jackson.group.chat.PostMessageRequest
import com.producement.koroonabot.team.TeamRepository
import org.springframework.stereotype.Service

@Service
class SlackService(
  private val slackClient: SlackClient,
  private val teamRepository: TeamRepository
) {

  fun sendAll(message: String) {
    val teams = teamRepository.findAll()
    for (team in teams) {
      slackClient.chat()
          .postMessage(team.bot.accessToken)
          .with(PostMessageRequest(text = message, channel = team.incomingWebhook!!.channel))
          .invoke()
    }
  }
}
