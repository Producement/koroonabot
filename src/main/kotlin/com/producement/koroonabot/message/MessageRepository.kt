package com.producement.koroonabot.message

import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {
  fun findTopByOrderByIdDesc(): Message
}
