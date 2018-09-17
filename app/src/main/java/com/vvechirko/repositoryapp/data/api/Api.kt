package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface Api {

    @GET("users")
    fun getUsers(): Observable<List<UserEntity>>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: String): Observable<UserEntity>

    @GET("albums")
    fun getAlbums(@Query("userId") userId: String): Observable<List<AlbumEntity>>

//    @GET("albums/{albumId}")
//    fun getAlbum(@Path("albumId") albumId: String): Observable<AlbumEntity>
//
//    @GET("photos")
//    fun getPhotos(@Query("albumId") albumId: String): Observable<List<PhotoEntity>>
//
//    @GET("photos/{photoId}")
//    fun getPhoto(@Path("photoId") photoId: String): Observable<PhotoEntity>

    @GET("comments")
    fun getComments(@Query("postId") postId: String): Observable<List<CommentEntity>>

    @GET("comments/{commentId}")
    fun getComment(@Path("commentId") commentId: String): Observable<CommentEntity>

    @GET("posts")
    fun getPosts(@Query("userId") userId: String?): Observable<List<PostEntity>>

    @GET("posts/{postId}")
    fun getPost(@Path("postId") postId: String): Observable<PostEntity>

    @FormUrlEncoded
    @POST("posts")
    fun createPosts(@FieldMap map: Map<String, String>): Observable<PostEntity>

    @FormUrlEncoded
    @PUT("posts/{postId}")
    fun updatePosts(@Path("postId") postId: String, @FieldMap map: Map<String, String>): Observable<PostEntity>

    @DELETE("posts/{postId}")
    fun deletePost(@Path("postId") postId: String): Completable

}