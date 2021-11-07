package com.example.posts.repo

import android.util.Log
import com.example.posts.db_retrofit.TypicodeApi
import com.example.posts.db_sqlite.PostDAO
import com.example.posts.model.PostModel
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostRepository (
    private val postDAO: PostDAO
) {

    val posts = postDAO.getAllPost()
    val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val TAG = "Post- PostRepository"

    suspend fun postApiCallCopyToDB() {
        val gson = Gson()
        val retrofit =  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()

        val restApi = retrofit.create<TypicodeApi>(TypicodeApi::class.java)
        restApi.getPosts()?.enqueue(object : Callback<List<PostModel?>?> {
            override fun onFailure(call: Call<List<PostModel?>?>, t: Throwable) {
                Log.d(TAG, "OOPS!! something went wrong..")
            }

            override fun onResponse(
                call: Call<List<PostModel?>?>,
                response: Response<List<PostModel?>?>
            ) {
                var posts = response.body()
                GlobalScope.launch {
                    postDAO.deleteAll()
                    posts?.forEach{
                        if (it != null) {
                            postDAO.insertPost(it)
                        }
                    }
                }
            }
        })
    }
}