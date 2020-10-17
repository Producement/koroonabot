package com.producement.koroonabot.dataprovider

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service

@Service
class OpenDataJsonClient(
  @Value("\${covid.opendata.url}") private val url: String,
  restTemplateBuilder: RestTemplateBuilder
) : DataProvider {

  private val restTemplate = restTemplateBuilder.build()

  override fun getLatestData(): String {
    val results = restTemplate.getForObject(url, Array<TestResult>::class.java)!!
    val positiveResults = results.filter { it.resultValue == "P" }.size
    return "Positiivseid teste $positiveResults"
  }

  data class TestResult(@JsonProperty("ResultValue") val resultValue: String)
}
