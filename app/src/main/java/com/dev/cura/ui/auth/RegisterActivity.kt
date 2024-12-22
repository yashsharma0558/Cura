package com.dev.cura.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dev.cura.R
import com.dev.cura.ui.viewmodel.AuthViewModel
import com.dev.cura.util.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RegisterActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        authViewModel.registerState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val response = state.data
                    Toast.makeText(this, response?.response, Toast.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val name = findViewById<EditText>(R.id.nameEditText).text.toString()
            val email = findViewById<EditText>(R.id.passwordEditText).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()
            val imagePath = "path/to/image.jpg" // Replace with file picker or camera image path.

            val profileImage = createMultipartBodyPart(imagePath, "profileImage")

            authViewModel.register(name, email, password, profileImage)
        }
    }

    private fun createMultipartBodyPart(filePath: String, partName: String): MultipartBody.Part {
        val file = File(filePath)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestBody)
    }

    private fun showLoading(isLoading: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
