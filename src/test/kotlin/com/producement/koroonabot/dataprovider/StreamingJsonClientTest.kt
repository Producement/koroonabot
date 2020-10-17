package com.producement.koroonabot.dataprovider

import com.fasterxml.jackson.core.JsonFactory
import com.producement.koroonabot.dataprovider.StreamingJsonClient
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
class StreamingJsonClientTest(private val mockServer: MockServerClient) {

  private lateinit var streamingJsonClient: StreamingJsonClient
  private lateinit var url: String

  @BeforeEach
  fun setUp() {
    url = "http://localhost:${mockServer.port}/opendata_covid19_test_results.json"
    streamingJsonClient = StreamingJsonClient(url, JsonFactory())
  }

  @Test
  fun getLatestData() {
    mockServer.`when`(
      request()
        .withMethod("GET")
        .withPath("/opendata_covid19_test_results.json"),
      Times.once()
    )
      .respond(
        response()
          .withStatusCode(200)
          .withBody(
            """
            [
               { "ResultValue":"N" },
               { "ResultValue":"P" },
               { "ResultValue":"N" },
               { "ResultValue":"P" }
            ]
            """
          )
      )

    val latestData = streamingJsonClient.getLatestData()

    assertThat(latestData).isEqualTo("Positiivseid teste 2")
  }
}
