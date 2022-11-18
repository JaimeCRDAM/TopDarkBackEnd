package com.example.services.user_services



data class CreateUserParams(
    val userName: String,
    val nickName: String,
    val password: String,
    val avatar: String,
    val experience: Int,
    val age: Int
)
