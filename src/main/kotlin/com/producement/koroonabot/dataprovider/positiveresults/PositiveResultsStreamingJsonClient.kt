package com.producement.koroonabot.dataprovider.positiveresults

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonToken
import com.producement.koroonabot.dataprovider.DataProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL

@Service
class PositiveResultsStreamingJsonClient(
  @Value("\${covid.opendata.testResults.url}") private val url: String,
  private val jsonFactory: JsonFactory
) : DataProvider {

  override fun getLatest(): Int {
    val parser = jsonFactory.createParser(URL(url))

    var positiveResults = 0
    while (parser.nextToken() != JsonToken.END_ARRAY) {
      if (parser.currentName == "ResultValue") {
        parser.nextToken()
        if (parser.text == "P") {
          positiveResults++
        }
      }
    }

    return positiveResults
  }
}
