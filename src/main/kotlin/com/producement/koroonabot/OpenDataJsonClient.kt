package com.producement.koroonabot

import com.fasterxml.jackson.databind.node.ArrayNode
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service

@Service
class OpenDataJsonClient(restTemplateBuilder: RestTemplateBuilder) : DataProvider {

  private val restTemplate = restTemplateBuilder.build()

  override fun getLatestData(): String {
    val results = restTemplate.getForObject(
      "https://opendata.digilugu.ee/opendata_covid19_test_results.json",
      ArrayNode::class.java
    )!!
    val positiveResults = results.filter { it.get("ResultValue").textValue() == "P" }.size
    return "Positiivseid teste $positiveResults"
  }
}
