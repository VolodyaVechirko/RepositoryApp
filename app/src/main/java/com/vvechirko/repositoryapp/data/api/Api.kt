package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface Api {

    // user
    @GET("users")
    fun getUsers(): Observable<List<UserEntity>>

    @GET("users/{userId}")
    fun getUser(
            @Path("userId") userId: String
    ): Observable<UserEntity>


    // comments
    @GET("comments")
    fun getComments(
            @Query("postId") postId: String?
    ): Observable<List<CommentEntity>>

    @GET("comments/{commentId}")
    fun getComment(
            @Path("commentId") commentId: String
    ): Observable<CommentEntity>

    @FormUrlEncoded
    @POST("comments")
    fun createComment(
            @FieldMap map: Map<String, String>
    ): Observable<CommentEntity>

    @FormUrlEncoded
    @PUT("comments/{commentsId}")
    fun updateComment(
            @Path("commentsId") commentsId: String,
            @FieldMap map: Map<String, String>
    ): Observable<CommentEntity>

    @DELETE("comments/{commentsId}")
    fun deleteComment(
            @Path("commentsId") commentsId: String
    ): Completable


    // posts
    @GET("posts")
    fun getPosts(
            @Query("userId") userId: String?
    ): Observable<List<PostEntity>>

    @GET("posts/{postId}")
    fun getPost(
            @Path("postId") postId: String
    ): Observable<PostEntity>

    @FormUrlEncoded
    @POST("posts")
    fun createPosts(
            @FieldMap map: Map<String, String>
    ): Observable<PostEntity>

    @FormUrlEncoded
    @PUT("posts/{postId}")
    fun updatePosts(
            @Path("postId") postId: String,
            @FieldMap map: Map<String, String>
    ): Observable<PostEntity>

    @DELETE("posts/{postId}")
    fun deletePost(
            @Path("postId") postId: String
    ): Completable

}