package com.example.predoctor.models.predictionresponse

import com.google.gson.annotations.SerializedName
import java.util.*


data class Odds(
    @SerializedName("1")
    val HomeW: Double,
    @SerializedName("12")
    val AnybodyW: Double,
    @SerializedName("1X")
    val HomeWD: Double,
    @SerializedName("2")
    val AwayW: Double,
    @SerializedName("X")
    val Draw: Double,
    @SerializedName("X2")
    val AwayWD: Double
)


