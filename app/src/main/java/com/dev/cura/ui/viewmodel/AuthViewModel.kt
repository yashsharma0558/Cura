package com.dev.cura.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.cura.data.api.LoginResponseDto
import com.dev.cura.data.api.RegisterResponseDto
import com.dev.cura.data.repository.AuthRepository
import com.dev.cura.domain.model.UserDetails
import com.dev.cura.domain.usecase.LoginUseCase
import com.dev.cura.domain.usecase.RegisterUseCase
import com.dev.cura.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<LoginResponseDto>>()
    val loginState: LiveData<Resource<LoginResponseDto>> = _loginState

    private val _registerState = MutableLiveData<Resource<RegisterResponseDto>>()
    val registerState: LiveData<Resource<RegisterResponseDto>> = _registerState

    private val _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails = _userDetails.asStateFlow()

    // Save user details
    fun saveUserDetails(name: String, email: String, photo: String, token: String) {
        viewModelScope.launch {
            authRepository.saveUserDetails(name, email, photo, token)
        }
    }

    // Load user details
    fun loadUserDetails() {
        viewModelScope.launch {
            authRepository.getUserDetails().collect { details ->
                _userDetails.value = details
            }
        }
    }

    // Clear user details (e.g., logout)
    fun clearUserDetails() {
        viewModelScope.launch {
            authRepository.clearUserDetails()
        }
    }

    fun login(email: String, password: String) {
        _loginState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = loginUseCase(email, password)
                _loginState.value = Resource.Success(response)
            } catch (e: Exception) {
                _loginState.value = Resource.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        profileImage: MultipartBody.Part?
    ) {
        _registerState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = registerUseCase(name, email, password, profileImage)
                _registerState.value = Resource.Success(response)
            } catch (e: Exception) {
                _registerState.value = Resource.Error(e.message ?: "An error occurred")
            }
        }
    }
}
