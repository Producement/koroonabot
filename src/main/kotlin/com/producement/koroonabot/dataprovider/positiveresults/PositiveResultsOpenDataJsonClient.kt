package com.producement.koroonabot.dataprovider.positiveresults

import com.fasterxml.jackson.annotation.JsonProperty
import com.producement.koroonabot.dataprovider.DataProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service

@Service
@Deprecated("Use StreamingJsonClient")
class PositiveResultsOpenDataJsonClient(
  @Value("\${covid.opendata.testResults.url}") private val url: String,
  restTemplateBuilder: RestTemplateBuilder
) : DataProvider {

  private val restTemplate = restTemplateBuilder.build()

  override fun getLatest(): Int {
    val results = restTemplate.getForObject(url, Array<TestResult>::class.java)!!
    return results.filter { it.resultValue == "P" }.size
  }

  data class TestResult(@JsonProperty("ResultValue") val resultValue: String)
}
