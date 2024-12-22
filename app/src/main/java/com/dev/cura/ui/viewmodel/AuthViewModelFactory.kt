package com.dev.cura.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.cura.data.repository.AuthRepository
import com.dev.cura.domain.usecase.LoginUseCase
import com.dev.cura.domain.usecase.RegisterUseCase

class AuthViewModelFactory(private val loginUseCase: LoginUseCase, private val registerUseCase: RegisterUseCase, private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(loginUseCase, registerUseCase, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
