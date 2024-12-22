package com.dev.cura.data.repository

import com.dev.cura.data.api.ApiService
import com.dev.cura.data.api.LoginDto
import com.dev.cura.data.api.LoginResponseDto
import com.dev.cura.data.api.RegisterResponseDto
import okhttp3.MultipartBody

class AuthRepository(private val apiService: ApiService) {
    suspend fun login(email: String, password: String): LoginResponseDto {
        return apiService.login(LoginDto(email, password))
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        profileImage: MultipartBody.Part?
    ): RegisterResponseDto {
        return apiService.register(name, email, password, profileImage)
    }
}

