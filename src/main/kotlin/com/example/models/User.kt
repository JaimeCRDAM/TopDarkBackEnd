package com.example.models




data class User(
    val nickname: String,
    val username: String,
    val lastLogin: String,
    var authToken: String? = null,
    var id: String
)