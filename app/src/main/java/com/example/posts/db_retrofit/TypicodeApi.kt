package com.example.posts.db_retrofit

import com.example.posts.model.CommentModel
import com.example.posts.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface TypicodeApi {
    @GET("posts")
    fun getPosts(): Call<List<PostModel?>?>?

    @GET("comments")
    fun getComments(): Call<List<CommentModel?>?>?
}