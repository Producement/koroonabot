package com.producement.koroonabot.team

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Team(
  @Id
  val teamId: String,
  val teamName: String,
  @OneToOne(cascade = [CascadeType.ALL])
  @JoinColumn(name = "incoming_webhook_id", referencedColumnName = "id")
  val incomingWebhook: IncomingWebhook?,
  @OneToOne(cascade = [CascadeType.ALL])
  @JoinColumn(name = "bot_id", referencedColumnName = "id")
  val bot: Bot) {
  fun toTeam(): com.kreait.slack.broker.store.team.Team {
    return com.kreait.slack.broker.store.team.Team(
        teamId = teamId,
        teamName = teamName,
        incomingWebhook = incomingWebhook?.toIncomingWebhook(),
        bot = bot.toBot()
    )
  }

  companion object {
    fun from(team: com.kreait.slack.broker.store.team.Team): Team {
      return Team(
          teamId = team.teamId,
          teamName = team.teamName,
          incomingWebhook = IncomingWebhook.from(
              team.incomingWebhook
          ),
          bot = Bot.from(team.bot)
      )
    }
  }
}

@Entity
data class Bot(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  val userId: String,
  val accessToken: String) {
  fun toBot(): com.kreait.slack.broker.store.team.Team.Bot {
    return com.kreait.slack.broker.store.team.Team.Bot(userId = userId, accessToken = accessToken)
  }

  companion object {
    fun from(bot: com.kreait.slack.broker.store.team.Team.Bot): Bot {
      return Bot(userId = bot.userId, accessToken = bot.accessToken)
    }
  }
}

@Entity
data class IncomingWebhook(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  val channel: String,
  val channelId: String,
  val configurationUrl: String,
  val url: String) {
  fun toIncomingWebhook(): com.kreait.slack.broker.store.team.Team.IncomingWebhook {
    return com.kreait.slack.broker.store.team.Team.IncomingWebhook(
        channel = channel,
        channelId = channelId,
        configurationUrl = configurationUrl,
        url = url
    )
  }

  companion object {
    fun from(incomingWebhook: com.kreait.slack.broker.store.team.Team.IncomingWebhook?): IncomingWebhook? {
      return if (incomingWebhook != null) IncomingWebhook(
          channel = incomingWebhook.channel,
          channelId = incomingWebhook.channelId,
          configurationUrl = incomingWebhook.configurationUrl,
          url = incomingWebhook.url
      ) else null
    }
  }
}
