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
    val message = Message(message = "Positiivseid teste 115")
    messageRepository.save(message)

    val foundMessage = messageRepository.findTopByMessageContainingOrderByIdDesc("Positiivseid teste")

    assertThat(foundMessage).isEqualTo(message)
  }

  @Test
  fun `can find latest message`() {
    val oldMessage = Message(message = "Positiivseid teste 100")
    messageRepository.save(oldMessage)
    val newMessage = Message(message = "Positiivseid teste 200")
    messageRepository.save(newMessage)

    val foundMessage = messageRepository.findTopByMessageContainingOrderByIdDesc("Positiivseid teste")

    assertThat(foundMessage).isEqualTo(newMessage)
  }
}
