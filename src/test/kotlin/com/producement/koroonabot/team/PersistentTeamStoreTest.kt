package com.producement.koroonabot.team

import com.kreait.slack.broker.store.team.Team
import com.kreait.slack.broker.store.team.Team.IncomingWebhook
import com.kreait.slack.broker.store.team.TeamNotFoundException
import com.producement.koroonabot.DbContainerInitializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ContextConfiguration(initializers = [DbContainerInitializer::class])
@AutoConfigureTestDatabase(replace = NONE)
@ExtendWith(SpringExtension::class)
class PersistentTeamStoreTest @Autowired constructor(teamRepository: TeamRepository) {

  private val teamStore: PersistentTeamStore =
      PersistentTeamStore(teamRepository)

  @Test
  fun `can put, find and remove a team`() {
    val incomingWebhook = IncomingWebhook("channel", "channelId", "configurationUrl", "url")
    val bot = Team.Bot("userId", "accessToken")
    val team = Team("teamId", "teamName", incomingWebhook, bot)
    teamStore.put(team)

    assertThat(teamStore.findById(team.teamId)).isEqualTo(team)
    teamStore.removeById(team.teamId)
    assertThrows<TeamNotFoundException> { teamStore.findById(team.teamId) }
  }
}
