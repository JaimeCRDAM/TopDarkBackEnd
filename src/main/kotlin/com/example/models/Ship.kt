package com.example.models

data class Ship(
    val plate: String,
    val type: String,
    val avatar: String,
    val firstCheck: Boolean, //Can cargo
    val secondCheck: Boolean //Can passengers
    )
