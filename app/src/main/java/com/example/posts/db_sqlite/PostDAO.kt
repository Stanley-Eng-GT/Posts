package com.example.posts.db_sqlite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.posts.model.PostModel

@Dao
interface PostDAO {
    @Query("SELECT * FROM Post")
    fun getAllPost(): LiveData<List<PostModel>>

    //return number of rows deleted
    @Query("DELETE FROM Post")
    suspend fun deleteAll() : Int

    //return id, -1 if nothing is inserted
    @Insert
    suspend fun insertPost(post: PostModel): Long
}