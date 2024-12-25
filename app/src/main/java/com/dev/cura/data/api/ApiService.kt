package com.dev.cura.data.api

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @POST("/post")
    suspend fun createPost(
        @Part("title") title: RequestBody,
        @Part("caption") caption: RequestBody,
        @Part image: MultipartBody.Part?
    ): CommonSuccessResponseDto

    @GET("/post")
    suspend fun getMyPost(): List<PostResponseDto>

    @GET("/post/show")
    suspend fun getLatestPost(): List<PostResponseAdvancedDto>

    @DELETE("/post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): CommonSuccessResponseDto

    @POST("/comment")
    suspend fun createComment(
        @Body commentDto: CommentDto
    ): CommonSuccessResponseDto

    @GET("/comment/{postId}")
    suspend fun getComment(
        @Path("postId") postId: Long
    ): List<CommentResponseDto>
    
    
    @GET("/like/{postId}")
    suspend fun likeDislikeToggler(
        @Path("postId") postId: Long
    ): CommonSuccessResponseDto

}

