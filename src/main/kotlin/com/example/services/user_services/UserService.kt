package com.example.services.user_services

import com.example.models.Pilot
import com.example.models.User
import com.example.utils.Response

interface UserService {
    suspend fun registerUser(params: CreateUserParams): User?
    suspend fun findUserByUsername(userName: String): User?
    suspend fun loginUser(params: LoginUserParams): User?
    suspend fun findRoleByUserName(username: String): String?
    suspend fun getAllPilots(): MutableList<Pilot>
}