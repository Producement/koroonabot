package com.producement.koroonabot.dataprovider.vaccinations

import com.fasterxml.jackson.core.JsonFactory
import com.producement.koroonabot.dataprovider.positiveresults.PositiveResultsStreamingJsonClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerExtension
import org.mockserver.matchers.Times
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response

@ExtendWith(MockServerExtension::class)
class VaccinationsStreamingJsonClientTest(private val mockServer: MockServerClient) {

  private lateinit var vaccinationsClient: VaccinationsStreamingJsonClient
  private lateinit var url: String

  @BeforeEach
  fun setUp() {
    url = "http://localhost:${mockServer.port}/opendata_covid19_vaccination_total.json"
    vaccinationsClient = VaccinationsStreamingJsonClient(url, JsonFactory())
  }

  @Test
  fun getLatestData() {
    mockServer.`when`(
      request()
        .withMethod("GET")
        .withPath("/opendata_covid19_vaccination_total.json"),
      Times.once()
    )
      .respond(
        response()
          .withStatusCode(200)
          .withBody(
            """
            [
              {
                "StatisticsDate": "2020-12-27",
                "TargetDiseaseCode": "219",
                "TargetDisease": "COVID-19",
                "VaccinationStatus": "InProgress",
                "DailyCount": 191,
                "TotalCount": 191,
                "PopulationCoverage": 0.014
              },
              {
                "StatisticsDate": "2020-12-28",
                "TargetDiseaseCode": "219",
                "TargetDisease": "COVID-19",
                "VaccinationStatus": "InProgress",
                "DailyCount": 319,
                "TotalCount": 510,
                "PopulationCoverage": 0.038
              }
            ]
            """
          )
      )

    val vaccinations = vaccinationsClient.getLatest()

    assertThat(vaccinations).isEqualTo(510)
  }
}
