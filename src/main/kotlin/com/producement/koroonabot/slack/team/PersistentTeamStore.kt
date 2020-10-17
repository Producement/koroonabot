package com.producement.koroonabot.slack.team

import com.kreait.slack.broker.store.team.Team
import com.kreait.slack.broker.store.team.TeamNotFoundException
import com.kreait.slack.broker.store.team.TeamStore
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PersistentTeamStore(
  private val teamRepository: TeamRepository
) : TeamStore {

  override fun findById(id: String): Team {
    val team = teamRepository.findByIdOrNull(id) ?: throw TeamNotFoundException("Team $id not found.")
    return team.toTeam()
  }

  override fun put(team: Team) {
    teamRepository.save(com.producement.koroonabot.slack.team.Team.from(team))
  }

  override fun removeById(id: String) {
    teamRepository.deleteById(id)
  }
}
