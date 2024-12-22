package com.dev.cura.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): LoginResponseDto

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part profileImage: MultipartBody.Part?,
    ): RegisterResponseDto

    @GET
    suspend fun verify( @Url verifyUrl: String ): ResponseBody
}

