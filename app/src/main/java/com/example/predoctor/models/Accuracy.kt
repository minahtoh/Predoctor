package com.example.predoctor.models

data class Accuracy(
    val last_14_days: Double,
    val last_30_days: Double,
    val last_7_days: Double,
    val yesterday: Double
)