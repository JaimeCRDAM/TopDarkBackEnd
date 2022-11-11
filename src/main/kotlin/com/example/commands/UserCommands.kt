package com.example.commands

import com.example.models.User
import com.example.services.CreateUserParams
import com.example.services.LoginUserParams
import com.example.utils.Response

interface UserCommands {
    suspend fun registerUser(params: CreateUserParams): Response<User?>
    suspend fun loginUser(params: LoginUserParams): Response<User?>
}