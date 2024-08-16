package de.nehranis

import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val URL = "https://192.168.2.154:8080"

class SensorAPI {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllThroughputs(): List<Throughput> {
        return httpClient.get(URL).body()
    }

    suspend fun getHourlyThroughputs(sinceDays: Int): List<Throughput> {
        return httpClient.get(URL) {
            parameter("sinceDays", sinceDays)
        }.body()
    }
}