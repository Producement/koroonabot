package com.producement.koroonabot.team

import com.producement.koroonabot.team.Team
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : CrudRepository<Team, String>
