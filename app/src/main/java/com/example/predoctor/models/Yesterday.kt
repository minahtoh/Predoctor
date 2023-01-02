package com.example.predoctor.models

data class Yesterday(
    val lost: Int,
    val pending: Int,
    val postponed: Int,
    val total: Int,
    val won: Int
)