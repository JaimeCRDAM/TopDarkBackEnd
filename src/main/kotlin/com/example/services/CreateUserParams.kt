package com.example.services

import kotlinx.serialization.*

@Serializable
data class CreateUserParams(
    val userName: String,
    val nickName: String,
    val password: String,
    val avatar: String,
)
