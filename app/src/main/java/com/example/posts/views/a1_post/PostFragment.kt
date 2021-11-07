package com.example.posts.views.a1_post

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posts.R
import com.example.posts.databinding.FragmentPostBinding
import com.example.posts.db_sqlite.SqliteDatabase
import com.example.posts.model.PostModel
import com.example.posts.repo.CommentRepository
import com.example.posts.repo.PostRepository

class PostFragment : Fragment() , AdpItmPost.OnItemClickListener {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var postViewModel: PostViewModel

    private val postRecycleList = mutableListOf<PostModel>()
    private val adapter = AdpItmPost(postRecycleList, this)

    private var searchTxt = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        //setup Database
        var sqlDb = SqliteDatabase.getInstance(requireActivity())

        //create repository, factory, View Model
        val postRepo = PostRepository(sqlDb.postDAO)
        val commentRepo = CommentRepository(sqlDb.commentDAO)
        val factory = PostViewModelFactory(postRepo, commentRepo)
        postViewModel = ViewModelProvider(requireActivity(), factory).get(PostViewModel::class.java)

        binding.lifecycleOwner = this
        binding.currentViewModel = postViewModel

        if (checkWifi()) {
            postViewModel.updatePost()
            postViewModel.updateComment()
        } else {
            Toast.makeText(requireActivity(), "No wifi- DB not updated", Toast.LENGTH_SHORT).show()
        }


        binding.frgPostSearchVw.onActionViewCollapsed()
        binding.frgPostSearchVw.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchTxt = newText
                }
                resetRecycleView()
                return false
            }
        })
        //Setup recycler view
        initRecyclerView()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkWifi(): Boolean {
        val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetwork
        return networkInfo != null
    }

    private fun initRecyclerView() {
        var layoutManager = LinearLayoutManager(context)
        binding.frgPostRcyVw.layoutManager = layoutManager
        binding.frgPostRcyVw.addItemDecoration(
            DividerItemDecoration(
                binding.frgPostRcyVw.context,
                layoutManager.orientation
            )
        )
        binding.frgPostRcyVw.adapter = adapter

        //observing data
        postViewModel.postLiveData.observe(requireActivity(), { resetRecycleView() })
    }

    private fun resetRecycleView() {
        postRecycleList.clear()
        val it = postViewModel.postLiveData.value

        if (it != null) {
            for (i in it) {
                if (searchTxt == "" || i.title.contains(searchTxt) || i.body.contains(searchTxt))
                    postRecycleList.add(i)
            }
        }
        binding.frgPostRcyVw.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("postId", postRecycleList[position].id)
        bundle.putString(
            "postInfo",
            "Title: ${postRecycleList[position].title}\nText: ${postRecycleList[position].body}\n"
        )
        binding.frgPostSearchVw.setQuery("", true)
        binding.frgPostSearchVw.onActionViewCollapsed()
        findNavController().navigate(R.id.action_postFragment_to_commentFragment, bundle)
    }
}