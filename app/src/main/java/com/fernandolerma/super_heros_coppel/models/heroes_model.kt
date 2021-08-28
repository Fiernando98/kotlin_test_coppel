package com.fernandolerma.super_heros_coppel.models

import com.google.gson.annotations.SerializedName

class HeroesModel(
    @SerializedName("id")
    val id: Int,
    val name: String,
    val slug: String,
    val powerstats: PowerStats,
    val appearance: Appearance,
    val biography: Biography,
    val work: Work,
    val connections: Connections,
    val images: Images
) {
    data class PowerStats(
        val intelligence: Int,
        val strength: Int,
        val speed: Int,
        val durability: Int,
        val power: Int,
        val combat: Int
    )

    data class Appearance(
        val gender: String,
        val race: String,
        val height: ArrayList<String>,
        val weight: ArrayList<String>,
        val eyeColor: String,
        val hairColor: String
    )

    data class Biography(
        val fullName: String,
        val alterEgos: String,
        val aliases: ArrayList<String>,
        val placeOfBirth: String,
        val firstAppearance: String,
        val publisher: String,
        val alignment: String
    )

    data class Work(
        val occupation: String,
        val base: String
    )

    data class Connections(
        val groupAffiliation: String,
        val relatives: String
    )

    data class Images(
        val xs: String?,
        val sm: String?,
        val md: String?,
        val lg: String?
    )
}