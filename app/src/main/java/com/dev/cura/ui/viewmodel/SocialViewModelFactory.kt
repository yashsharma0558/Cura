package com.dev.cura.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.cura.data.repository.AuthRepository
import com.dev.cura.data.repository.SocialRepository
import com.dev.cura.domain.usecase.LoginUseCase
import com.dev.cura.domain.usecase.RegisterUseCase

class SocialViewModelFactory(private val socialRepository: SocialRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SocialViewModel::class.java)) {
            return SocialViewModel(socialRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}