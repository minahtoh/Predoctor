package com.example.predoctor.models.predoctorresults

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Predictions")
data class Prediction(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val away_team: String,
    val home_team: String,
    val prediction: String,
    val odds:Double?,
    val competition_name: String,
    val start_date: String
)
