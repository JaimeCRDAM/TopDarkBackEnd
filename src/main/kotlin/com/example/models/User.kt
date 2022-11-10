package com.example.models

import org.bson.types.ObjectId
import kotlinx.serialization.*


@Serializable
data class User(
    @Contextual val id: ObjectId? = null,
    val createdAt: String,
    val lastLogin: String,
    val avatar: String,
    val nickname: String,
    val username: String,
    var authToken: String? = null,
) {
    override fun toString(): String {
        return "User(id=$id, createdAt='$createdAt', lastLogin='$lastLogin', avatar='$avatar', nickname='$nickname', username='$username', authToken=$authToken)"
    }
}