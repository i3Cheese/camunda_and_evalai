package ru.i3cheese.camundakotlin.evalai

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

interface EvalAISessionInt {
    fun doPost(url: String, data: String): String
    fun doGet(url: String): String
}

object EvalAISession : EvalAISessionInt {
    init {
        doAuth()
    }

    private fun doAuth() {
        val url = "$baseUrl/api/auth/login/"

        val data = Json.encodeToString(
            AuthBody(username, password)
        )
        println(data)
        val request = Request.Builder()
            .url(url)
            .post(data.toRequestBody("application/json".toMediaType()))
            .build()
        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()!!
            token = Json.parseToJsonElement(body).jsonObject["token"]!!.jsonPrimitive.content
        } catch (e: Exception) {
            token = ""
            println(e)
        }
    }

    override fun doPost(url: String, data: String): String {
        if (token == "") {
            doAuth()
        }
        val request = Request.Builder()
            .url("$baseUrl$url")
            .addHeader("Authorization", "Token $token")
            .post(data.toRequestBody("application/json".toMediaType()))
            .build()
        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("Response code: ${response.code}")
            }
            val body = response.body?.string()!!
            println("DOPOST $url")
            println(body)
            return body
        } catch (e: Exception) {
            println(e)
            return "" 
        }
    }
    override fun doGet(url: String): String {
        if (token == "") {
            doAuth()
        }
        val request = Request.Builder()
            .url("$baseUrl$url")
            .addHeader("Authorization", "Token $token")
            .get()
            .build()
        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()!!
            println("DOGET $url")
            println(body)
            return body
        } catch (e: Exception) {
            println(e)
            return ""
        }
    }

    @Serializable
    private data class AuthBody(val username: String, val password: String)

    private var token: String = ""
    private val client = OkHttpClient()
    private const val baseUrl = "http://localhost:8000"
    private const val username = "admin"
    private const val password = "password"
}
