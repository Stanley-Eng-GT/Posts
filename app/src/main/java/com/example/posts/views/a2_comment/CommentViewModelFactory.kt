package com.example.posts.views.a2_comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.posts.repo.CommentRepository

class CommentViewModelFactory  (
    private val commentRepo: CommentRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            return CommentViewModel(commentRepo) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}