package com.example.services

import com.example.models.Globals.USER_ROLE
import com.example.models.User
import com.example.models.objects.Connection.dbQuery
import com.example.models.objects.Connection.getDocumentById
import com.example.models.objects.Connection.getDocumentByUserName
import com.example.models.objects.Connection.getRoleByUserName
import com.example.models.objects.Connection.insertIntoTable
import com.example.security.hash
import com.mongodb.client.result.InsertOneResult
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime

class UserServiceImpl : UserService {
    override suspend fun registerUser(params: CreateUserParams): User? {
        lateinit var statement: InsertOneResult
        val userDocument = Document()
        val roleDocument = Document()
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

            statement = insertIntoTable("usuarios", "users", userDocument)
            insertedId = statement.insertedId?.asObjectId()?.value!!
            roleDocument.append("_id", insertedId).append("role", USER_ROLE)
            insertIntoTable(table = "roles", document = roleDocument)
            resultDocument = getDocumentById("usuarios", "users", insertedId)
        }

        return documentToUser(resultDocument)
    }

    override suspend fun findRoleByUserName(username: String): String {
        return getRoleByUserName(userName = username)
    }

    override suspend fun loginUser(params: LoginUserParams): User? {
        var resultDocument: Document? = null
        dbQuery {
            resultDocument = getDocumentByUserName(username = params.credential.name)
        }
        val dbPassword = resultDocument?.get("password") as String
        val salt = dbPassword.split(":")[0]
        if (hash(params.credential.password, salt) == dbPassword) {
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
            )
        }
    }

    override suspend fun findUserByUsername(userName: String): User? {
        var resultDocument: Document? = null
        dbQuery{
            resultDocument = getDocumentByUserName(username = userName)
        }
        return documentToUser(resultDocument)
    }
}
