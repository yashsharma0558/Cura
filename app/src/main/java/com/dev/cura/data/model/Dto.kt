package com.dev.cura.data.model

import okhttp3.MultipartBody


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

data class PostDto(
    val imageUrl: Int,
    val title: String?,
    val caption: String?,
    val updatedAt: String?,
    val userName: String?,
    val userImageUrl: Int,
    val id: String?,
    var likes: Int?,
    var liked: Boolean?,
    val commentsList: List<CommentDto>?
)

data class CreatePostDto(
    val image: MultipartBody.Part,
    val title: String,
    val caption: String
)

data class CreateCommentDto(
    val postId: Int,
    val content: String
)

data class CommentDto(
    val id: Int?,
    val content: String?,
    val userName: String?,
    val userImageUrl: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val userId: Int?
)




