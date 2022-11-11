package com.example.services

import com.example.models.User
import com.example.models.objects.Connection.dbQuery
import com.example.models.objects.Connection.getDocumentById
import com.example.models.objects.Connection.getDocumentByUserName
import com.example.models.objects.Connection.insertIntoTable
import com.example.security.hash
import com.mongodb.client.result.InsertOneResult
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime

class UserServiceImpl : UserService {
    override suspend fun registerUser(params: CreateUserParams): User? {
        lateinit var statement: InsertOneResult
        val document: Document = Document()
        lateinit var insertedId: ObjectId
        var resultDocument: Document? = null
        dbQuery{
            document
                .append("userName", params.userName)
                .append("nickName", params.nickName)
                .append("avatar", params.avatar)
                .append("password", hash(params.password))
                .append("createdAt", LocalDateTime.now())
                .append("lastLogin", "")
            statement = insertIntoTable("usuarios","users", document)
            insertedId = statement.insertedId?.asObjectId()?.value!!
            resultDocument = getDocumentById("usuarios","users", insertedId)
        }

        return documentToUser(resultDocument)
    }

    override suspend fun findUserByUsername(userName: String): User? {
        var resultDocument: Document? = null
        dbQuery{
            resultDocument = getDocumentByUserName(userName)
        }
        return documentToUser(resultDocument)
    }

    override suspend fun loginUser(userName: String, password: String) {
        TODO("Not yet implemented")
    }

    private fun documentToUser(row: Document?): User?{

        return if(row == null) null
        else {
            val id = row["_id"].toString()
            User(
            id = id,
            createdAt = row["createdAt"].toString(),
            lastLogin = row["lastLogin"].toString(),
            avatar = row["avatar"] as String,
            nickname = row["nickName"] as String,
            username = row["userName"] as String,
            )
        }
    }
}
