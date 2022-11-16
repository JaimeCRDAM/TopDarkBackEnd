package com.example.services.userservices

import com.example.models.User

interface UserService {
    suspend fun registerUser(params: CreateUserParams): User?
    suspend fun findUserByUsername(userName: String): User?
    suspend fun loginUser(params: LoginUserParams): User?
    suspend fun findRoleByUserName(username: String): String?
}