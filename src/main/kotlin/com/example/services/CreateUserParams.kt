package com.example.services



data class CreateUserParams(
    val userName: String,
    val nickName: String,
    val password: String,
    val avatar: String,
)
