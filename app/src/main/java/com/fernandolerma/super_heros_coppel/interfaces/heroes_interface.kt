package com.fernandolerma.super_heros_coppel.interfaces

import com.fernandolerma.super_heros_coppel.models.HeroesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface HeroesInterface {
    @GET("all.json")
    fun getAllList(@HeaderMap headers: Map<String, String>): Call<ArrayList<HeroesModel>>
}