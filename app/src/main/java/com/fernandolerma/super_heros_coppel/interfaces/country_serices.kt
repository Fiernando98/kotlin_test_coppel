package com.fernandolerma.super_heros_coppel.interfaces

import com.fernandolerma.super_heros_coppel.models.CountryModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface CountryService {

    @GET("countries")
    fun getCountryList(@HeaderMap headers: Map<String, String>): Call<List<CountryModel>>
}