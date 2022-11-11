package com.example.models




data class User(
    val id: String? = null,
    val createdAt: String,
    val lastLogin: String,
    val avatar: String,
    val nickname: String,
    val username: String,
    var authToken: String? = null,
)