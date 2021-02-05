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
                "StatisticsDate": "2021-02-03",
                "TargetDiseaseCode": "219",
                "TargetDisease": "COVID-19",
                "VaccinationStatus": "InProgress",
                "DailyCount": 1708,
                "TotalCount": 31858,
                "PopulationCoverage": 2.397
              },
              {
                "StatisticsDate": "2021-02-03",
                "TargetDiseaseCode": "219",
                "TargetDisease": "COVID-19",
                "VaccinationStatus": "Completed",
                "DailyCount": 1526,
                "TotalCount": 14325,
                "PopulationCoverage": 1.078
              },
              {
                "StatisticsDate": "2021-02-04",
                "TargetDiseaseCode": "219",
                "TargetDisease": "COVID-19",
                "VaccinationStatus": "Completed",
                "DailyCount": 1546,
                "TotalCount": 15871,
                "PopulationCoverage": 1.194
              },
              {
                "StatisticsDate": "2021-02-04",
                "TargetDiseaseCode": "219",
                "TargetDisease": "COVID-19",
                "VaccinationStatus": "InProgress",
                "DailyCount": 2069,
                "TotalCount": 33927,
                "PopulationCoverage": 2.553
              }
            ]
            """
          )
      )

    val vaccinations = vaccinationsClient.getLatest()

    assertThat(vaccinations).isEqualTo(15871 + 33927)
  }
}
