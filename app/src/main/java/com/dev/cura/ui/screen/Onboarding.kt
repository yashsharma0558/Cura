package com.dev.cura.ui.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dev.cura.R
import com.dev.cura.data.api.RetrofitClient
import com.dev.cura.data.repository.AuthRepository
import com.dev.cura.domain.usecase.LoginUseCase
import com.dev.cura.domain.usecase.RegisterUseCase
import com.dev.cura.ui.screen.addictionselect.SelectAddictionActivity
import com.dev.cura.ui.screen.auth.LoginActivity
import com.dev.cura.ui.screen.home.HomeActivity
import com.dev.cura.ui.viewmodel.AuthViewModel
import com.dev.cura.ui.viewmodel.AuthViewModelFactory
import kotlinx.coroutines.launch

class Onboarding : AppCompatActivity() {
    val apiService = RetrofitClient.instance
    val repository = AuthRepository(this, apiService)
    val loginUseCase = LoginUseCase(repository)
    val registerUseCase = RegisterUseCase(repository)
    val factory = AuthViewModelFactory(loginUseCase, registerUseCase, repository)
    private lateinit var intent: Intent

    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        authViewModel = factory.create(AuthViewModel::class.java)

        lifecycleScope.launch {
            val prefsDetails = getUserDetails(this@Onboarding)
            // Use user details, e.g., navigate to Home screen
            println("Welcome back, ${prefsDetails["name"]}")
            val isValidToken = prefsDetails["token"]?.let { it1 -> validateToken(it1) }
            println("Welcome back, ${prefsDetails["token"]}")
            val hasCompletedSurvey = prefsDetails["hasCompletedSurvey"].toBoolean()
            if(hasCompletedSurvey){
                intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
            else if (isValidToken == true) {
                intent = Intent(applicationContext, SelectAddictionActivity::class.java)
                startActivity(intent)
            } else {
                intent = Intent(applicationContext, LoginActivity::class.java)
            }

        }
        findViewById<Button>(R.id.nextButton).setOnClickListener {
            try {
                startActivity(intent)
            }
            catch(e: Exception){
                println(e.message)
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        }
    }

    private suspend fun validateToken(token: String): Boolean {
        if (token.isEmpty()) {
            return false
        }
        // Call the server to validate the token
        return try {
            val response = apiService.verify("/auth/verify?token=${token}") // Replace with your actual API call
            response.string() == "Your Email is Verified"
        } catch (e: Exception) {
            Log.e("OnboardingActivity", "Token validation failed: ${e.message}")
            false
        }
    }

    fun getUserDetails(context: Context): Map<String, String?> {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return mapOf(
            "name" to sharedPreferences.getString("name", null),
            "email" to sharedPreferences.getString("email", null),
            "photo" to sharedPreferences.getString("photo", null),
            "token" to sharedPreferences.getString("token", null),
            "hasCompletedSurvey" to sharedPreferences.getBoolean("hasCompletedSurvey", false).toString()
        )
    }


}