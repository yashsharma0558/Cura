package com.dev.cura.data.api



data class LoginDto(
    val email: String,
    val password: String,
)

data class LoginResponseDto (
    val u_id: Int,
    val name: String,
    val email: String ,
    val image_url: String? ,
    val token: String,
)


data class RegisterResponseDto (
    val response : String
)


