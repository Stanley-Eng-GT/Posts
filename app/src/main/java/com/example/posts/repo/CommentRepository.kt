package com.example.posts.repo

import android.util.Log
import com.example.posts.db_retrofit.TypicodeApi
import com.example.posts.db_sqlite.CommentDAO
import com.example.posts.model.CommentModel
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentRepository  (
    private val commentDAO: CommentDAO
) {
    val comments = commentDAO.getAllComments()
    val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val TAG = "Post- PostRepository"

    suspend fun commentApiCallCopyToDB() {
        val gson = Gson()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()

        val restApi = retrofit.create<TypicodeApi>(TypicodeApi::class.java)
        restApi.getComments()?.enqueue(object : Callback<List<CommentModel?>?> {
            override fun onFailure(call: Call<List<CommentModel?>?>, t: Throwable) {
                Log.d(TAG, "OOPS!! something went wrong..")
            }

            override fun onResponse(
                call: Call<List<CommentModel?>?>,
                response: Response<List<CommentModel?>?>
            ) {
                var comments = response.body()
                GlobalScope.launch {
                    commentDAO.deleteAll()
                    comments?.forEach {
                        if (it != null) {
                            commentDAO.insertComment(it)
                        }
                    }
                }
            }
        })
    }
}