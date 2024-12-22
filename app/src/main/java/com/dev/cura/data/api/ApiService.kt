package com.dev.cura.data.api

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("/api/login")
    suspend fun login(@Body loginDto: LoginDto): LoginResponseDto

    @Multipart
    @POST("/api/register")
    suspend fun register(
        @Part("name") name: String,
        @Part("email") email: String,
        @Part("password") password: String,
        @Part profileImage: MultipartBody.Part?
    ): RegisterResponseDto
}

