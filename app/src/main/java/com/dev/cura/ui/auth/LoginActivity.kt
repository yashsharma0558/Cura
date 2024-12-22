package com.dev.cura.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dev.cura.R
import com.dev.cura.data.repository.AuthRepository
import com.dev.cura.ui.viewmodel.AuthViewModel
import com.dev.cura.ui.viewmodel.AuthViewModelFactory
import com.dev.cura.util.Resource

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        authViewModel.loginState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val response = state.data
                    Toast.makeText(this, "Welcome ${response?.name}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    showLoading(false)
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
    }
    private fun showLoading(isLoading: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
