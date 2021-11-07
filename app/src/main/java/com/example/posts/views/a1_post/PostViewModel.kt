package com.example.posts.views.a1_post

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posts.repo.CommentRepository
import com.example.posts.repo.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val postRepo: PostRepository, private val commentRepo: CommentRepository): ViewModel(),
    Observable {

    var searchTxt = ""
    val postLiveData = postRepo.posts

    fun updatePost()= viewModelScope.launch{
        postRepo.postApiCallCopyToDB()
    }

    fun updateComment()= viewModelScope.launch{
        commentRepo.commentApiCallCopyToDB()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

}