package com.producement.koroonabot

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.producement.koroonabot.dataprovider.DataProvider
import com.producement.koroonabot.slack.message.Message
import com.producement.koroonabot.slack.message.MessageRepository
import com.producement.koroonabot.slack.SlackService
import org.junit.jupiter.api.Test

class KoroonaBotTest {

    private val slackService: SlackService = mock()
    private val messageRepository: MessageRepository = mock()
    private val dataProvider: DataProvider = mock()

    private val koroonaBot = KoroonaBot(slackService, messageRepository, dataProvider)

    @Test
    fun `downloads latest data from terviseamet website and sends it to all slack teams`() {
        val oldMessage = "Kokku on Eestis koroonaviirus diagnoositud 100 inimesel."
        val newMessage = "Kokku on Eestis koroonaviirus diagnoositud 200 inimesel."

        whenever(messageRepository.findTopByOrderByIdDesc()).thenReturn(Message(message = oldMessage))
        whenever(dataProvider.getLatestData()).thenReturn(newMessage)

        koroonaBot.poller()

        verify(slackService).sendAll(newMessage)
    }

    @Test
    fun `does not send duplicate messages`() {
        val message = "Kokku on Eestis koroonaviirus diagnoositud 115 inimesel."

        whenever(messageRepository.findTopByOrderByIdDesc()).thenReturn(Message(message = message))
        whenever(dataProvider.getLatestData()).thenReturn(message)

        koroonaBot.poller()

        verifyZeroInteractions(slackService)
    }

    @Test
    fun `does not send duplicate messages on new data`() {
        val oldMessage = "Kokku on Eestis koroonaviirus diagnoositud 100 inimesel."
        val newMessage = "Kokku on Eestis koroonaviirus diagnoositud 200 inimesel."

        whenever(messageRepository.findTopByOrderByIdDesc()).thenReturn(Message(message = oldMessage))
            .thenReturn(Message(message = newMessage))
        whenever(dataProvider.getLatestData()).thenReturn(newMessage)

        koroonaBot.poller()
        koroonaBot.poller()

        verify(messageRepository, times(1)).save(Message(message = newMessage))
        verify(slackService, times(1)).sendAll(newMessage)
    }
}
