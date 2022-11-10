package com.example.commands

import com.example.models.User
import com.example.services.CreateUserParams
import com.example.utils.Response

interface UserCommands {
    suspend fun registerUser(params: CreateUserParams): Response<User?>
    suspend fun loginUser(userName: String, password: String): Response<User?>
}