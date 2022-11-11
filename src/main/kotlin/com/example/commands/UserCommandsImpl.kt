package com.example.commands

import com.example.models.User
import com.example.security.JwtConfig
import com.example.services.CreateUserParams
import com.example.services.LoginUserParams
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
                val token = JwtConfig.instance.createAccessToken(user.username)
                user.authToken = token
                Response.SuccessResponse(data = user)
            } else {
                Response.ErrorResponse(message = "Invalid Data")
            }
        }
    }

    override suspend fun loginUser(params: LoginUserParams): Response<User?> {
        return if (!doUsernameExist(params.credential.name)){
            Response.ErrorResponse(message = "Username doesn't exists")
        } else {
            val user = userService.loginUser(params)
            if(user != null){
                val token = JwtConfig.instance.createAccessToken(user.username)
                user.authToken = token
                Response.SuccessResponse(data = user)
            } else {
                Response.ErrorResponse(message = "Invalid username/password")
            }
        }
    }

    private suspend fun doUsernameExist(username: String): Boolean{
        return userService.findUserByUsername(username) != null
    }
}