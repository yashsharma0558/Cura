package com.dev.cura.ui.screen.auth

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dev.cura.R
import com.dev.cura.data.api.ApiService
import com.dev.cura.data.api.RetrofitClient
import com.dev.cura.data.repository.AuthRepository
import com.dev.cura.domain.usecase.LoginUseCase
import com.dev.cura.domain.usecase.RegisterUseCase
import com.dev.cura.ui.viewmodel.AuthViewModel
import com.dev.cura.ui.viewmodel.AuthViewModelFactory
import com.dev.cura.util.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var imageView: ImageView
    private lateinit var imagePath: MultipartBody.Part

    // Register the activity result contract
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            handleImageUri(uri)
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val apiService = RetrofitClient.instance
        val repository = AuthRepository(this, apiService)
        val loginUseCase = LoginUseCase(repository)
        val registerUseCase = RegisterUseCase(repository)
        val factory = AuthViewModelFactory(loginUseCase, registerUseCase, repository)
        authViewModel = factory.create(AuthViewModel::class.java)

        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            // Launch the image picker
            imagePickerLauncher.launch("image/*")
        }

        authViewModel.registerState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> ""
                is Resource.Success -> {
//                    showLoading(false)
                    val response = state.data
                    Toast.makeText(this, response?.response, Toast.LENGTH_SHORT).show()
                    finish()
                }

                is Resource.Error -> {
//                    showLoading(false)
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val name = findViewById<EditText>(R.id.nameEditText).text.toString().trim()
            val email = findViewById<EditText>(R.id.passwordEditText).text.toString().trim()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()
//            val profileImage = createMultipartBodyPart(imagePath, "profileImage")

            authViewModel.register(name, email, password, imagePath)
        }
    }
//
//    private fun createMultipartBodyPart(filePath: String, partName: String): MultipartBody.Part {
//        val file = File(filePath)
//        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
//        return MultipartBody.Part.createFormData(partName, file.name, requestBody)
//    }

    // Handle the image URI
    private fun handleImageUri(uri: Uri) {
        // Set the image in the ImageView for preview
        imageView.setImageURI(uri)
        compressAndConvertImage(this, uri)

        // Get the file path for further usage
        val filePath = getFilePathFromUri(uri)
        val compressedFile = compressAndConvertImage(this, uri)

        if (compressedFile != null) {
            val requestBody = compressedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imagePath =
                MultipartBody.Part.createFormData("profileImage", compressedFile.name, requestBody)

        } else {
            Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this, "Image Path: $filePath", Toast.LENGTH_LONG).show()
    }

    // Convert URI to file path (requires API level 19+)
    private fun getFilePathFromUri(uri: Uri): String {
        val projection = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
                return cursor.getString(columnIndex)
            }
        }
        return uri.path ?: "Unknown Path"
    }

    fun compressAndConvertImage(context: Context, imageUri: Uri, maxFileSizeInBytes: Int = 2 * 1024 * 1024): File? {
        try {
            // Load the image as a Bitmap
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            // Compress the image to JPG format
            val outputStream = ByteArrayOutputStream()
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

            // Reduce size iteratively until it fits within the maxFileSizeInBytes
            var quality = 80
            while (outputStream.size() > maxFileSizeInBytes && quality > 10) {
                outputStream.reset()
                quality -= 10
                originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            }

            // Save the compressed Bitmap to a temporary file
            val compressedFile = File(context.cacheDir, "compressed_image.jpg")
            val fileOutputStream = FileOutputStream(compressedFile)
            fileOutputStream.write(outputStream.toByteArray())
            fileOutputStream.close()

            return compressedFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
//        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

}
