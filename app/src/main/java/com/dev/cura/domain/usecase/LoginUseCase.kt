package com.dev.cura.domain.usecase

import com.dev.cura.data.api.LoginResponseDto
import com.dev.cura.data.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): LoginResponseDto {
        return authRepository.login(email, password)
    }
}
