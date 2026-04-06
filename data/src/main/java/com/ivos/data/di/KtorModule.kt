package com.ivos.data.di

import com.ivos.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val defaultTimeout = 30L

@[Module InstallIn(SingletonComponent::class)]
object KtorModule {
    @[Provides Singleton]
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        println("KTOR LOG $message")
                    }
                }
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            defaultRequest {
                url(BuildConfig.BASE_URL)
                headers {
                    append("App-Token", BuildConfig.APP_TOKEN)
                    append(HttpHeaders.Authorization, "Bearer ${BuildConfig.AUTH_TOKEN}")
                    append(HttpHeaders.Accept, "application/json")
                }
            }

            engine {
                config {
                    connectTimeout(defaultTimeout, TimeUnit.SECONDS)
                    readTimeout(defaultTimeout, TimeUnit.SECONDS)
                }
            }
        }
    }
}
