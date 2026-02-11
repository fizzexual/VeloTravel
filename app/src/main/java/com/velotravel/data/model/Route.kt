package com.velotravel.data.model

data class Route(
    val id: String,
    val name: String,
    val startCity: String,
    val endCity: String,
    val totalKm: Double,
    val milestones: List<Milestone>,
    val description: String = ""
)

data class Milestone(
    val kmFromStart: Double,
    val cityName: String,
    val description: String = ""
)
