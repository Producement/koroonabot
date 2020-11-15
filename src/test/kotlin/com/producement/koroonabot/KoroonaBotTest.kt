package com.producement.koroonabot

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
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
    fun `downloads latest data from the data provider and sends it to all slack teams`() {
        val oldMessage = "Positiivseid teste 100"
        val newNumber = 200

        whenever(messageRepository.findTopByOrderByIdDesc()).thenReturn(Message(message = oldMessage))
        whenever(dataProvider.getLatestPositiveTests()).thenReturn(newNumber)

        koroonaBot.poll()

        verify(slackService).sendAll("Positiivseid teste 200")
    }

    @Test
    fun `does not send duplicate messages`() {
        val positiveResults = 115
        val message = "Positiivseid teste $positiveResults"

        whenever(messageRepository.findTopByOrderByIdDesc()).thenReturn(Message(message = message))
        whenever(dataProvider.getLatestPositiveTests()).thenReturn(positiveResults)

        koroonaBot.poll()

        verify(messageRepository, times(1)).findTopByOrderByIdDesc()
        verifyNoMoreInteractions(messageRepository)
        verifyZeroInteractions(slackService)
    }

    @Test
    fun `does not send duplicate messages on new data`() {
        val oldMessage = "Positiivseid teste 100"
        val newPositiveResults = 200
        val newMessage = "Positiivseid teste $newPositiveResults"

        whenever(messageRepository.findTopByOrderByIdDesc()).thenReturn(Message(message = oldMessage))
            .thenReturn(Message(message = newMessage))
        whenever(dataProvider.getLatestPositiveTests()).thenReturn(newPositiveResults)

        koroonaBot.poll()
        koroonaBot.poll()

        verify(messageRepository, times(1)).save(Message(message = newMessage))
        verify(slackService, times(1)).sendAll(newMessage)
    }

    @Test
    fun `does not send duplicate messages when numbers decrease for some reason`() {
        val oldMessage = "Positiivseid teste 100"
        val newPositiveResults = 0

        whenever(messageRepository.findTopByOrderByIdDesc()).thenReturn(Message(message = oldMessage))
        whenever(dataProvider.getLatestPositiveTests()).thenReturn(newPositiveResults)

        koroonaBot.poll()

        verify(messageRepository, times(1)).findTopByOrderByIdDesc()
        verifyNoMoreInteractions(messageRepository)
        verifyZeroInteractions(slackService)
    }
}
