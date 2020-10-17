package com.producement.koroonabot.slack.message

import com.producement.koroonabot.DbContainerInitializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
class MessageRepositoryTest @Autowired constructor(
  private val messageRepository: MessageRepository
) {

  @Test
  fun `can insert and find latest message`() {
    val message = Message(message = "Kokku on Eestis koroonaviirus diagnoositud 115 inimesel.")
    messageRepository.save(message)

    val foundMessage = messageRepository.findTopByOrderByIdDesc()

    assertThat(foundMessage).isEqualTo(message)
  }

  @Test
  fun `can find latest message`() {
    val oldMessage = Message(message = "Kokku on Eestis koroonaviirus diagnoositud 100 inimesel.")
    messageRepository.save(oldMessage)
    val newMessage = Message(message = "Kokku on Eestis koroonaviirus diagnoositud 200 inimesel.")
    messageRepository.save(newMessage)

    val foundMessage = messageRepository.findTopByOrderByIdDesc()

    assertThat(foundMessage).isEqualTo(newMessage)
  }
}
