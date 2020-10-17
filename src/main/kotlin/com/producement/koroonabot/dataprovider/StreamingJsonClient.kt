package com.producement.koroonabot.dataprovider

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL

@Service
class StreamingJsonClient(
  @Value("\${covid.opendata.url}") private val url: String,
  private val jsonFactory: JsonFactory
) : DataProvider {

  override fun getLatestData(): String {
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

    return "Positiivseid teste $positiveResults"
  }
}
