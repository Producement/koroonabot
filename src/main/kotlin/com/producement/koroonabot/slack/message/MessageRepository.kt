package com.producement.koroonabot.slack.message

import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {
  fun findTopByMessageContainingOrderByIdDesc(message: String): Message?
}
