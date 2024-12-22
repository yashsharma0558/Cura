package com.dev.cura.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val profileImage: String = "",
    val mobile: String = "",
    val posts: List<Post>
)

data class Post(
    val description: String,
    val likes: Int,
    val comments: List<Comment>,
)

data class Comment(
    val message: String,
    val time: Long,
    val author: User
)

