package com.example.commands.user_commands

import com.example.models.Pilot
import com.example.models.User
import com.example.models.objects.Connection
import com.example.security.JwtConfig
import com.example.services.user_services.CreateUserParams
import com.example.services.user_services.LoginUserParams
import com.example.services.user_services.UserService
import com.example.utils.Response
import io.ktor.http.*

class UserCommandsImpl(
    private val userService: UserService
) : UserCommands {
    override suspend fun registerUser(params: CreateUserParams): Response<User?> {
        return if (doUsernameExist(params.userName)){
            Response(message = "Username already exists", statusCode = HttpStatusCode.OK)
        } else {
            val user = userService.registerUser(params)
            if(user != null){
                Response(message = "User created", statusCode = HttpStatusCode.OK)
            } else {
                Response(message = "Invalid Data", statusCode = HttpStatusCode.OK)
            }
        }
    }

    override suspend fun loginUser(params: LoginUserParams): Response<User?> {
        return if (!doUsernameExist(params.credential.name)){
            Response(message = "Username doesn't exist", statusCode = HttpStatusCode.OK)
        } else {
            val user = userService.loginUser(params)
            if(user != null){
                val token = JwtConfig.instance.createAccessToken(user.username)
                user.authToken = token
                Response(data = user, statusCode = HttpStatusCode.OK)
            } else {
                Response(message = "Invalid username/password", statusCode = HttpStatusCode.OK)
            }
        }
    }

    override suspend fun findUserByName(name: String): Response<User?> {
        val user = userService.findUserByUsername(name)!!
        val token = JwtConfig.instance.createAccessToken(user.username)
        user.authToken = token
        return Response(data = user, statusCode = HttpStatusCode.OK)
    }

    override suspend fun getAllPilots(): Response<MutableList<Pilot>> {
        val result = userService.getAllPilots()
        return if (result.size == 0)
            Response(message = "No pilot data", statusCode = HttpStatusCode.OK)
        else
            Response(data = result, statusCode = HttpStatusCode.OK)
    }

    private suspend fun doUsernameExist(username: String): Boolean{
        return userService.findUserByUsername(username) != null
    }
}