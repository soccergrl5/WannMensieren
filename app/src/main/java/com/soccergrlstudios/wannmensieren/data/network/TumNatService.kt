package com.soccergrlstudios.wannmensieren.data.network

import com.soccergrlstudios.wannmensieren.data.api.TumNatApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TumNatService {
    private const val DEFAULT_BASE_URL = "https://api.srv.nat.tum.de/"

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun createApi(baseUrl: String = DEFAULT_BASE_URL): TumNatApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TumNatApi::class.java)
    }

    val api: TumNatApi by lazy { createApi() }
}