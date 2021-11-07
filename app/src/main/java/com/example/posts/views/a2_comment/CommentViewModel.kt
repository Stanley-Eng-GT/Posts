package com.example.posts.views.a2_comment

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posts.repo.CommentRepository

class CommentViewModel (commentRepo: CommentRepository): ViewModel(), Observable {
    var searchTxt=""
    val commentLiveData = commentRepo.comments

    @Bindable
    var postInfo = MutableLiveData<String>("Comment List")

    var postId = -1

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}