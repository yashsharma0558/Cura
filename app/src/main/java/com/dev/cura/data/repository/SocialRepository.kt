package com.dev.cura.data.repository

import com.dev.cura.data.api.ApiService
import com.dev.cura.data.model.CommentDto
import com.dev.cura.data.model.CreateCommentDto
import com.dev.cura.data.model.CreatePostDto
import com.dev.cura.data.model.PostDto

class SocialRepository(private val apiService: ApiService) {

    // Get all posts
    suspend fun getAllPosts(token:String): List<PostDto>? {
        val response = apiService.getAllPosts(token)
        return if (response.isSuccessful) response.body() else null
    }

    // Create a post
    suspend fun createPost(createPostDto: CreatePostDto): PostDto? {
        val response = apiService.createPost(createPostDto.image, createPostDto.title, createPostDto.caption)
        return if (response.isSuccessful) response.body() else null
    }

    // Get comments for a specific post
    suspend fun getComments(postId: Int): List<CommentDto>? {
        val response = apiService.getComments(postId)
        return if (response.isSuccessful) response.body() else null
    }

    // Create a comment
    suspend fun createComment(postId: Int, content: String): CommentDto? {
        val createCommentDto = CreateCommentDto(postId, content)
        val response = apiService.createComment(postId, createCommentDto)
        return if (response.isSuccessful) response.body() else null
    }
}
