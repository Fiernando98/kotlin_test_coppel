package com.fernandolerma.super_heros_coppel.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HeroesServiceBuilder {
    private const val URL = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/"
    private val okHttp = OkHttpClient.Builder()
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    private val retrofit = builder.build()
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}