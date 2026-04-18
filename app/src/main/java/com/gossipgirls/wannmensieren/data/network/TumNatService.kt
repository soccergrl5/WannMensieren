package com.gossipgirls.wannmensieren.data.network

import com.gossipgirls.wannmensieren.data.api.TumNatApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TumNatService {
    private const val BASE_URL = "https://demo.campus.tum.de/"

    val api: TumNatApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TumNatApi::class.java)
    }
}
