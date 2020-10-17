package com.producement.koroonabot

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RestClientTest(OpenDataJsonClient::class)
class OpenDataJsonClientTest(
  @Autowired private val openDataJsonClient: OpenDataJsonClient,
  @Autowired private val mockRestServiceServer: MockRestServiceServer
) {

  @Test
  fun getLatestData() {
    val json = """
      [
         {
            "id":"68faefde73c0744a4135afbd98004a59e968b2b8c287483bae152788155f8ea2",
            "Gender":"N",
            "AgeGroup":"40-44",
            "Country":"Eesti",
            "County":"Harju maakond",
            "ResultValue":"P",
            "StatisticsDate":"2020-04-25",
            "ResultTime":"2020-04-24T14:10:00+03:00",
            "AnalysisInsertTime":"2020-04-25T11:55:30+03:00"
         },
         {
            "id":"22211c4c579237f79d98280d58497f7d985f43b80df47627d9c8074bec8094ad",
            "Gender":"M",
            "AgeGroup":"30-34",
            "Country":"Eesti",
            "County":"Saare maakond",
            "ResultValue":"N",
            "StatisticsDate":"2020-04-25",
            "ResultTime":"2020-04-24T08:41:00+03:00",
            "AnalysisInsertTime":"2020-04-25T11:57:10+03:00"
         }
      ]
    """
    mockRestServiceServer
      .expect(requestTo("https://opendata.digilugu.ee/opendata_covid19_test_results.json"))
      .andRespond(withSuccess(json, MediaType.APPLICATION_JSON))

    val latestData = openDataJsonClient.getLatestData()

    assertThat(latestData).isEqualTo("Positiivseid teste 1")
  }
}
