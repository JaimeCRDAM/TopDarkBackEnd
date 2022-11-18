package com.example.services.user_services

import com.example.models.Globals.USER_ROLE
import com.example.models.Pilot
import com.example.models.User
import com.example.models.objects.Connection
import com.example.models.objects.Connection.dbQuery
import com.example.models.objects.Connection.getUserByUserName
import com.example.models.objects.Connection.getRoleByUserName
import com.example.models.objects.Connection.insertIntoTable
import com.example.security.hash
import com.example.utils.Response
import com.mongodb.client.result.InsertOneResult
import io.ktor.http.*
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime

class UserServiceImpl : UserService {
    override suspend fun registerUser(params: CreateUserParams): User? {
        lateinit var statement: InsertOneResult
        val userDocument = Document()
        lateinit var insertedId: ObjectId
        var resultDocument: Document? = null
        dbQuery {
            userDocument
                .append("username", params.userName)
                .append("nickname", params.nickName)
                .append("avatar", params.avatar)
                .append("password", hash(params.password))
                .append("createdAt", LocalDateTime.now())
                .append("lastLogin", "")
                .append("allMissions", 0)
                .append("failedMissions", 0)
                .append("age", params.age)
                .append("experience", params.experience)
                .append("role", USER_ROLE)

            statement = insertIntoTable("usuarios", "users", userDocument)
            insertedId = statement.insertedId?.asObjectId()!!.value
        }
        resultDocument = getUserByUserName(username = params.userName)

        return documentToUser(resultDocument)
    }

    override suspend fun findRoleByUserName(username: String): String {
        return getRoleByUserName(userName = username)
    }

    override suspend fun loginUser(params: LoginUserParams): User? {
        var resultDocument: Document? = null
        dbQuery {
            resultDocument = getUserByUserName(username = params.credential.name)
        }
        val dbPassword = resultDocument?.get("password") as String
        val salt = dbPassword.split(":")[0]
        val hash = hash(params.credential.password, salt)
        if ( hash == dbPassword) {
            return documentToUser(resultDocument)
        }
        return null
    }

    private fun documentToUser(document: Document?): User?{
        return if(document == null) null
        else {
            User(
            nickname = document["nickname"] as String,
            username = document["username"] as String,
                lastLogin = document["lastLogin"] as String,
            id = document["_id"].toString()
            )
        }
    }

    override suspend fun findUserByUsername(userName: String): User? {
        var resultDocument: Document? = null
        dbQuery{
            resultDocument = getUserByUserName(username = userName)
        }
        return documentToUser(resultDocument)
    }

    override suspend fun getAllPilots(): MutableList<Pilot> {
        val result = Connection.getAllPilots().map {
            documentToPilot(it)
        }
        return result.toMutableList()
    }

    private suspend fun documentToPilot(document: Document): Pilot{
        val success = if( (document["allMissions"] as Int) == 0) 1.0 else (document["allMissions"] as Int)/(document["failedMissions"] as Int)+.0
        return Pilot(
            _id = document["_id"].toString(),
        nickName = document["nickname"] as String,
        avatar = document["avatar"] as String,
        experience = document["experience"] as Int,
        age = document["age"] as Int,
        success = success,
        )

    }
}
