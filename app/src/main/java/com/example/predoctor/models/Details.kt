package com.example.predoctor.models

data class Details(
    val last_14_days: Last14Days,
    val last_30_days: Last14Days,
    val last_7_days: Last14Days,
    val yesterday: Yesterday
)