package com.dev.cura.data.repository

import android.content.Context
import android.util.Log
import com.dev.cura.data.api.ApiService
import com.dev.cura.data.model.LoginDto
import com.dev.cura.data.model.LoginResponseDto
import com.dev.cura.data.model.RegisterResponseDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepository(private val context: Context, private val apiService: ApiService) {
    suspend fun login(email: String, password: String): LoginResponseDto {
        return apiService.login(LoginDto(email, password))
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        profileImage: MultipartBody.Part?
    ): RegisterResponseDto {
        Log.d("REGISTRATION_DEBUG", "$name $email $password $profileImage")
        return apiService.register(name.toRequestBody("text/plain".toMediaType()), email.toRequestBody("text/plain".toMediaType()), password.toRequestBody("text/plain".toMediaType()), profileImage)
    }

}

