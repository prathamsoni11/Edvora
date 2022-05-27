package com.example.edvora

import org.json.JSONArray

data class Edvora(
    val ride_id: Int = 0,
    val map_url: String ="",
    val date: String = "",
    val city: String = "",
    val state: String = "",
    val origin_station: Int = 0,
    val distance: Int = 0,
    val station_path: String,
    val temp: Int = 0,

)