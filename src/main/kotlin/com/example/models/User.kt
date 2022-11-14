package com.example.models




data class User(
    val nickname: String,
    val username: String,
    var authToken: String? = null,
)