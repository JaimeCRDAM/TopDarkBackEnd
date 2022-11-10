package com.example.security

import io.ktor.util.*
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

val SECRET_KEY = "5324189135478"
val ALGORITHM = "HmacSHA1"

val HASH_KEY = hex(SECRET_KEY)
val HMAC_KEY = SecretKeySpec(HASH_KEY, ALGORITHM)

fun hash(password:String):String{
    val hmac = Mac.getInstance(ALGORITHM)
    hmac.init(HMAC_KEY)
    val salt = randomStringByKotlinRandom()
    password.plus(salt)
    return salt.plus(":"+hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8))))
}

const val charPool = "ABCDEFGHIJKLMNOPqRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"

fun randomStringByKotlinRandom() = (1..16)
    .map{ Random.nextInt(0, charPool.length).let { charPool[it] }}
    .joinToString("")