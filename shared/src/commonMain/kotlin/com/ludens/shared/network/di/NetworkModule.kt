package com.ludens.shared.network.di

import com.ludens.shared.core.BuildVariant
import com.ludens.shared.network.service.GamesApi
import com.ludens.shared.network.service.GamesApiImpl
import com.ludens.shared.network.util.AUTHORIZATION_KEY
import com.ludens.shared.network.util.CLIENT_ID_KEY
import com.ludens.shared.network.util.bearerToken
import com.ludens.shared.requirements.platformEngine
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IGDB_HTTP_CLIENT = "IGDB_HTTP_CLIENT"

val networkModule = module {

    single(named(IGDB_HTTP_CLIENT)) {
        HttpClient(platformEngine()) {

            val buildVariant: BuildVariant = get()

            install(JsonFeature) {
                val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                serializer = KotlinxSerializer(json)
            }

            defaultRequest {
                host = buildVariant.igdbHost
                url { protocol = URLProtocol.HTTPS }
                header(CLIENT_ID_KEY, buildVariant.igdbClientId)
                header(AUTHORIZATION_KEY, bearerToken(buildVariant.igdbToken))
            }

            install(Logging) {
                level = when (buildVariant) {
                    is BuildVariant.Develop -> LogLevel.ALL
                    is BuildVariant.Release -> LogLevel.INFO
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
            }
        }
    }

    single<GamesApi> { GamesApiImpl(get(named(IGDB_HTTP_CLIENT))) }
}
