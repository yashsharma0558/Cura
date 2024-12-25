package com.dev.cura.data.api



data class LoginDto(
    val email: String,
    val password: String,
)

data class CommentDto(
    val content: String,
    val postId: Long
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

data class CommonSuccessResponseDto (
    val response : String
)

data class PostResponseDto(
    val id : Long,
    val user_id : Long,
    val title : String,
    val caption : String,
    val imageUrl: String,
    val commentList: List<CommentResponseDto>,
    val likeList: List<LikeResponseDto>
)

data class PostResponseAdvancedDto(
    val id : Long,
    val userName: String,
    val userImageUrl: String,
    val title : String,
    val caption : String,
    val imageUrl: String,
    val commentList: List<CommentResponseDto>,
    val likes: Int,
    val liked: Boolean,
)

data class CommentResponseDto(
    val id : Long,
    val user_id : Long,
    val post_id : Long,
    val content : String,
)

data class LikeResponseDto(
    val id : Long,
    val userId : Long,
    val postId : Long,
)






