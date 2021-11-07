package com.example.posts.views.a1_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.posts.repo.CommentRepository
import com.example.posts.repo.PostRepository

class PostViewModelFactory (
    private val postRepo: PostRepository,
    private val commentRepo: CommentRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(postRepo, commentRepo) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}