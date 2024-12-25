package com.dev.cura.data.repository

import com.dev.cura.data.api.ApiService
import com.dev.cura.data.api.CommentDto
import com.dev.cura.data.api.CommentResponseDto
import com.dev.cura.data.api.CommonSuccessResponseDto
import com.dev.cura.data.api.PostResponseAdvancedDto
import com.dev.cura.data.api.PostResponseDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class PostRepository( private val apiService: ApiService) {

    suspend fun createPost(
        title: String,
        caption: String,
        image: MultipartBody.Part?
    ): CommonSuccessResponseDto {
        return apiService.createPost(title.toRequestBody("text/plain".toMediaType()), caption.toRequestBody("text/plain".toMediaType()), image)
    }

    suspend fun deletePost(
        postId: Long
    ): CommonSuccessResponseDto {
        return apiService.deletePost(postId)
    }

    suspend fun getLatestPost(): List<PostResponseAdvancedDto>{
        return apiService.getLatestPost()
    }

    suspend fun getMyPost(): List<PostResponseDto>{
        return apiService.getMyPost()
    }

    suspend fun createComment(
        postId: Long,
        comment: String
    ): CommonSuccessResponseDto{
        return apiService.createComment(CommentDto(comment, postId))
    }

    suspend fun deleteComment(){}

    suspend fun getAllComments(
        postId: Long
    ): List<CommentResponseDto>{
        return apiService.getComment(postId)
    }

    suspend fun updateComment(){}

    suspend fun likeDislikeToggler(
        postId: Long
    ): CommonSuccessResponseDto{
        return apiService.likeDislikeToggler(postId)
    }


}