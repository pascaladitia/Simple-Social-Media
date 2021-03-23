package com.pascal.udacodingpagging3.network

import com.pascal.udacodingpagging3.model.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiService {

    //post
    @GET("posts")
    suspend fun getPost(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<ResponseListPost>

    @GET("posts/{postId}")
    fun detailPost(@Path("postId") postId: Int): Flowable<ResponseListPost>

    @GET("posts/{postId}")
    fun deletePost(@Path("postId") postId: Int): Flowable<ResponseListPost>

    @POST("posts")
    fun addPost(
        @Query("title") title: String,
        @Query("body") body: String,
        @Query("userId") userid: Int
    ): Flowable<ResponseListPost>

    @PUT("posts/{postId}")
    fun updatePost(
        @Path("postId") postId: Int,
        @Query("title") title: String,
        @Query("body") body: String,
        @Query("userId") userid: Int
    ): Flowable<ResponseListPost>

    @GET("posts/{postId}/comments")
    suspend fun commentPost(
        @Path("postId") postId: Int,
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<ResponseCommentId>



    //todos
    @GET("todos")
    suspend fun getTodo(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<ResponseListTodo>



    //user
    @GET("users")
    fun getUser(): Flowable<List<ResponseListUser>>

    @GET("users/{userId}/posts")
    suspend fun getPostuserId(
        @Path("userId") userId: Int,
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<ResponseListPost>

    @GET("users/{userId}/albums")
    suspend fun getAlmbumUserId(
        @Path("userId") userId: Int,
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<ResponseListAlbumUserId>

    @GET("users/{userId}/posts")
    suspend fun getTodoUserId(
        @Path("userId") userId: Int,
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<ResponseListTodo>

    @GET("albums/{albumId}/photos")
    suspend fun getPhotoAlbumId(
        @Path("albumId") albumId: Int,
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<ResponsePhotoAlbumId>
}