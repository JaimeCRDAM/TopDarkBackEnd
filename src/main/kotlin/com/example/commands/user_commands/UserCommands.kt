package com.example.commands.user_commands

import com.example.models.Pilot
import com.example.models.User
import com.example.services.user_services.CreateUserParams
import com.example.services.user_services.LoginUserParams
import com.example.utils.Response

interface UserCommands {
    suspend fun registerUser(params: CreateUserParams): Response<User?>
    suspend fun loginUser(params: LoginUserParams): Response<User?>
    suspend fun findUserByName(name: String): Response<User?>
    suspend fun getAllPilots(): Response<MutableList<Pilot>>

}