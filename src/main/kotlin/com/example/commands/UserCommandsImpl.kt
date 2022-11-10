package com.example.commands

import com.example.models.User
import com.example.security.JwtConfig
import com.example.services.CreateUserParams
import com.example.services.UserService
import com.example.utils.Response

class UserCommandsImpl(
    private val userService: UserService
) : UserCommands {
    override suspend fun registerUser(params: CreateUserParams): Response<User?> {
        return if (doUsernameExist(params.userName)){
            Response.ErrorResponse(message = "Username already exists")
        } else {
            val user = userService.registerUser(params)
            if(user != null){
                val token = JwtConfig.instance.createAccessToken(user.id.toString())
                user.authToken = token
                Response.SucessResponse(data = user)
            } else {
                Response.ErrorResponse(message = "Invalid Data")
            }
        }
    }

    override suspend fun loginUser(userName: String, password: String): Response<User?> {
        TODO("Not yet implemented")
    }

    private suspend fun doUsernameExist(username: String): Boolean{
        return userService.findUserByUsername(username) != null
    }
}