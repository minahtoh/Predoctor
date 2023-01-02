package com.example.predoctor.models.predictionresponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.time.format.FormatStyle


data class Data(
    val away_team: String,
    val competition_cluster: String,
    val competition_name: String,
    val federation: String,
    val home_team: String,
    val id: Int,
    val is_expired: Boolean,
    val last_update_at: String,
    val market: String,
    val odds: Odds,
    val prediction: String,
    val result: String,
    val season: String,
    val start_date: String,
    val status: String
):Serializable

    fun Data.formattedTime():String{
    val firstApiFormat = ISO_LOCAL_DATE_TIME
    val date = LocalDate.parse(start_date , firstApiFormat)
        val time = LocalTime.parse(start_date,firstApiFormat)
       return date.dayOfWeek.toString().lowercase()

    }
