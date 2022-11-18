package com.example.models

data class Mission(
    val _id: String,
    val amount: Int,
    val firstCheck: Boolean,
    val secondCheck: Boolean,
    val missionType: String
)
