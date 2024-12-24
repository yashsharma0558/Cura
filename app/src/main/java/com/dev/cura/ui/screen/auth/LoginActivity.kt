package com.dev.cura.ui.screen.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.cura.R
import com.dev.cura.data.api.RetrofitClient
import com.dev.cura.data.repository.AuthRepository
import com.dev.cura.domain.usecase.LoginUseCase
import com.dev.cura.domain.usecase.RegisterUseCase
import com.dev.cura.ui.screen.addictionselect.SelectAddictionActivity
import com.dev.cura.ui.viewmodel.AuthViewModel
import com.dev.cura.ui.viewmodel.AuthViewModelFactory
import com.dev.cura.util.Resource

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val apiService = RetrofitClient.instance
        val repository = AuthRepository(this, apiService)
        val loginUseCase = LoginUseCase(repository)
        val registerUseCase = RegisterUseCase(repository)
        val factory = AuthViewModelFactory(loginUseCase, registerUseCase, repository)
        authViewModel = factory.create(AuthViewModel::class.java)
        authViewModel.loginState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> ""
                is Resource.Success -> {
//                    showLoading(false)
                    val response = state.data
                    Toast.makeText(this, "Welcome ${response?.name}", Toast.LENGTH_SHORT).show()
                    if (response != null) {
                        response.image_url?.let {
                            saveUserDetails(this, response.name, response.email,
                                it, response.token)
                        }
                        Log.d("LoginResponse", "Here's your ${response}")
                    }
                    val intent = Intent(this, SelectAddictionActivity::class.java)
                    startActivity(intent)

                }

                is Resource.Error -> {
//                    showLoading(false)
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val email = findViewById<EditText>(R.id.passwordEditText).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()
            authViewModel.login(email, password)
        }

        findViewById<TextView>(R.id.txtSignup).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.textView).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
    fun saveUserDetails(context: Context, name: String, email: String, photo: String, token: String) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("photo", photo)
        editor.putString("token", token)
        editor.putBoolean("hasCompletedSurvey", false)
        editor.apply() // Commit changes asynchronously
    }


}
