package com.dev.cura.domain.usecase

import com.dev.cura.data.api.RegisterResponseDto
import com.dev.cura.data.repository.AuthRepository
import okhttp3.MultipartBody

class RegisterUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        profileImage: MultipartBody.Part?
    ): RegisterResponseDto {
        return authRepository.register(name, email, password, profileImage)
    }
}
