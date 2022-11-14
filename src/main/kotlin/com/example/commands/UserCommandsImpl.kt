package com.example.commands

import com.example.models.User
import com.example.security.JwtConfig
import com.example.services.CreateUserParams
import com.example.services.LoginUserParams
import com.example.services.UserService
import com.example.utils.Response
import io.ktor.http.*

class UserCommandsImpl(
    private val userService: UserService
) : UserCommands {
    override suspend fun registerUser(params: CreateUserParams): Response<User?> {
        return if (doUsernameExist(params.userName)){
            Response(message = "Username already exists", statusCode = HttpStatusCode.Unauthorized)
        } else {
            val user = userService.registerUser(params)
            if(user != null){
                val token = JwtConfig.instance.createAccessToken(user.username)
                user.authToken = token
                Response(data = user, statusCode = HttpStatusCode.Created)
            } else {
                Response(message = "Invalid Data", statusCode = HttpStatusCode.NotAcceptable)
            }
        }
    }

    override suspend fun loginUser(params: LoginUserParams): Response<User?> {
        return if (!doUsernameExist(params.credential.name)){
            Response(message = "Username doesn't exist", statusCode = HttpStatusCode.Unauthorized)
        } else {
            val user = userService.loginUser(params)
            if(user != null){
                val token = JwtConfig.instance.createAccessToken(user.username)
                user.authToken = token
                Response(data = user, statusCode = HttpStatusCode.Accepted)
            } else {
                Response(message = "Invalid username/password", statusCode = HttpStatusCode.Unauthorized)
            }
        }
    }

    override suspend fun findUserByName(name: String): Response<User?> {
        val user = userService.findUserByUsername(name)!!
        val token = JwtConfig.instance.createAccessToken(user.username)
        user.authToken = token
        return Response(data = user, statusCode = HttpStatusCode.Accepted)
    }

    private suspend fun doUsernameExist(username: String): Boolean{
        return userService.findUserByUsername(username) != null
    }
}