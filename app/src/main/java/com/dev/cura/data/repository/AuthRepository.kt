package com.dev.cura.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dev.cura.MyApp.Companion.dataStore
import com.dev.cura.data.api.ApiService
import com.dev.cura.data.api.LoginDto
import com.dev.cura.data.api.LoginResponseDto
import com.dev.cura.data.api.RegisterResponseDto
import com.dev.cura.domain.model.PreferencesKeys
import com.dev.cura.domain.model.UserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    suspend fun saveUserDetails(name: String, email: String, photo: String, token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NAME] = name
            preferences[PreferencesKeys.EMAIL] = email
            preferences[PreferencesKeys.PHOTO] = photo
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    // Get user details as Flow
    fun getUserDetails(): Flow<UserDetails> {
        return context.dataStore.data.map { preferences ->
            UserDetails(
                name = preferences[PreferencesKeys.NAME] ?: "",
                email = preferences[PreferencesKeys.EMAIL] ?: "",
                photo = preferences[PreferencesKeys.PHOTO] ?: "",
                token = preferences[PreferencesKeys.TOKEN] ?: ""
            )
        }
    }

    // Clear user details (e.g., on logout)
    suspend fun clearUserDetails() {
        context.dataStore.edit { it.clear() }
    }
}

