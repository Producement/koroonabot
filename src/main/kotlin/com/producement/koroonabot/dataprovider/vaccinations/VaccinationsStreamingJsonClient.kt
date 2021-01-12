package com.producement.koroonabot.dataprovider.vaccinations

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonToken
import com.producement.koroonabot.dataprovider.DataProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL

@Service
class VaccinationsStreamingJsonClient(
  @Value("\${covid.opendata.vaccinations.url}") private val url: String,
  private val jsonFactory: JsonFactory
) : DataProvider {

  override fun getLatest(): Int {
    val parser = jsonFactory.createParser(URL(url))

    var vaccinations = 0
    while (parser.nextToken() != JsonToken.END_ARRAY) {
      if (parser.currentName == "TotalCount") {
        parser.nextToken()
        if (parser.intValue > vaccinations) {
          vaccinations = parser.intValue;
        }
      }
    }

    return vaccinations
  }
}
