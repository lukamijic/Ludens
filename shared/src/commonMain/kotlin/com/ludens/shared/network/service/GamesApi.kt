package com.ludens.shared.network.service

import com.ludens.shared.network.model.GameResponse
import io.ktor.client.*
import io.ktor.client.request.*

interface GamesApi {
    suspend fun fetchGames(): List<GameResponse>
}

internal class GamesApiImpl(private val client: HttpClient): GamesApi {

    private val gamesPath = "/v4/games"

    override suspend fun fetchGames(): List<GameResponse> =
        client.post(gamesPath) {
            body = "fields *; limit 50;"
        }
}
