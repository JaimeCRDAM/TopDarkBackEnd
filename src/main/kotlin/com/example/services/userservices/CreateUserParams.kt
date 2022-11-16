package com.example.services.userservices



data class CreateUserParams(
    val userName: String,
    val nickName: String,
    val password: String,
    val avatar: String,
    val experience: Int,
)
