package com.producement.koroonabot

import com.producement.koroonabot.dataprovider.DataProvider
import com.producement.koroonabot.slack.message.Message
import com.producement.koroonabot.slack.message.MessageRepository
import com.producement.koroonabot.slack.SlackService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class KoroonaBotTest {

    private val slackService: SlackService = mock()
    private val messageRepository: MessageRepository = mock()
    private val positiveResultsClient: DataProvider = mock()
    private val vaccinationsClient: DataProvider = mock()

    private val koroonaBot = KoroonaBot(slackService, messageRepository, positiveResultsClient, vaccinationsClient)

    @Test
    fun `downloads latest data from the data provider and sends it to all slack teams`() {
        val oldMessage = "Positiivseid teste 100"
        val newNumber = 200

        whenever(messageRepository.findTopByMessageContainingOrderByIdDesc("Positiivseid teste"))
            .thenReturn(Message(message = oldMessage))
        whenever(positiveResultsClient.getLatest()).thenReturn(newNumber)

        koroonaBot.positiveTests()

        verify(slackService).sendAll("Positiivseid teste 200")
    }

    @Test
    fun `does not send duplicate messages`() {
        val positiveResults = 115
        val message = "Positiivseid teste $positiveResults"

        whenever(messageRepository.findTopByMessageContainingOrderByIdDesc("Positiivseid teste"))
            .thenReturn(Message(message = message))
        whenever(positiveResultsClient.getLatest()).thenReturn(positiveResults)

        koroonaBot.positiveTests()

        verify(messageRepository, times(1)).findTopByMessageContainingOrderByIdDesc("Positiivseid teste")
        verifyNoMoreInteractions(messageRepository)
        verifyNoInteractions(slackService)
    }

    @Test
    fun `does not send duplicate messages on new data`() {
        val oldMessage = "Positiivseid teste 100"
        val newPositiveResults = 200
        val newMessage = "Positiivseid teste $newPositiveResults"

        whenever(messageRepository.findTopByMessageContainingOrderByIdDesc("Positiivseid teste"))
            .thenReturn(Message(message = oldMessage))
            .thenReturn(Message(message = newMessage))
        whenever(positiveResultsClient.getLatest()).thenReturn(newPositiveResults)

        koroonaBot.positiveTests()
        koroonaBot.positiveTests()

        verify(messageRepository, times(1)).save(Message(message = newMessage))
        verify(slackService, times(1)).sendAll(newMessage)
    }

    @Test
    fun `does not send duplicate messages when numbers decrease for some reason`() {
        val oldMessage = "Positiivseid teste 100"
        val newPositiveResults = 0

        whenever(messageRepository.findTopByMessageContainingOrderByIdDesc("Positiivseid teste"))
            .thenReturn(Message(message = oldMessage))
        whenever(positiveResultsClient.getLatest()).thenReturn(newPositiveResults)

        koroonaBot.positiveTests()

        verify(messageRepository, times(1)).findTopByMessageContainingOrderByIdDesc("Positiivseid teste")
        verifyNoMoreInteractions(messageRepository)
        verifyNoInteractions(slackService)
    }

    @Test
    fun `sends vaccination data as well`() {
        val oldPositiveTests = "Positiivseid teste 100"
        val oldVaccinations = "Vaktsineerimisi 200"
        val newPositiveResults = 300
        val newVaccinationResults = 400
        val newPositiveTests = "Positiivseid teste $newPositiveResults"
        val newVaccinations = "Vaktsineerimisi $newVaccinationResults"

        whenever(messageRepository.findTopByMessageContainingOrderByIdDesc("Positiivseid teste"))
            .thenReturn(Message(message = oldPositiveTests))
        whenever(messageRepository.findTopByMessageContainingOrderByIdDesc("Vaktsineerimisi"))
            .thenReturn(Message(message = oldVaccinations))
        whenever(positiveResultsClient.getLatest()).thenReturn(newPositiveResults)
        whenever(vaccinationsClient.getLatest()).thenReturn(newVaccinationResults)

        koroonaBot.pollLatestData()

        verify(messageRepository, times(1)).findTopByMessageContainingOrderByIdDesc("Positiivseid teste")
        verify(messageRepository, times(1)).findTopByMessageContainingOrderByIdDesc("Vaktsineerimisi")
        verify(messageRepository, times(1)).save(Message(message = newPositiveTests))
        verify(messageRepository, times(1)).save(Message(message = newVaccinations))
        verify(slackService, times(1)).sendAll(newPositiveTests)
        verify(slackService, times(1)).sendAll(newVaccinations)
    }
}
