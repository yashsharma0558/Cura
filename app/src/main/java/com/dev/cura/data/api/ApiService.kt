package com.dev.cura.data.api

import com.dev.cura.data.model.CommentDto
import com.dev.cura.data.model.CreateCommentDto
import com.dev.cura.data.model.LoginDto
import com.dev.cura.data.model.LoginResponseDto
import com.dev.cura.data.model.PostDto
import com.dev.cura.data.model.RegisterResponseDto
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): LoginResponseDto

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part profileImage: MultipartBody.Part?,
    ): RegisterResponseDto

    @GET
    suspend fun verify( @Url verifyUrl: String ): ResponseBody

    // GET request to retrieve remedy JSON
    @GET("auth/remedy")
    suspend fun getRemedy(
        @Query("email") email: String
    ): Response<JsonObject>

    // POST request to update remedy JSON
    @POST("auth/remedy")
    suspend fun setRemedy(
        @Query("email") email: String,
        @Body remedy: JsonObject
    ): Response<JsonObject>

    // Get all posts
    @GET("post")
    suspend fun getAllPosts(
        @Query("token") token: String
    ): Response<List<PostDto>>

    // Create a post
    @Multipart
    @POST("post")
    suspend fun createPost(
        @Part image: MultipartBody.Part,
        @Part("title") title: String,
        @Part("caption") caption: String
    ): Response<PostDto>

    // Get comments for a specific post
    @GET("comment/{postId}/comments")
    suspend fun getComments(
        @Path("postId") postId: Int
    ): Response<List<CommentDto>>

    // Create a comment
    @POST("posts/{postId}/comments")
    suspend fun createComment(
        @Path("postId") postId: Int,
        @Body comment: CreateCommentDto
    ): Response<CommentDto>
}

