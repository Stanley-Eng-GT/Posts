package com.example.posts.views.a2_comment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posts.R
import com.example.posts.databinding.FragmentCommentBinding
import com.example.posts.db_sqlite.SqliteDatabase
import com.example.posts.model.CommentModel
import com.example.posts.repo.CommentRepository

class CommentFragment : Fragment() {
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var commentViewModel: CommentViewModel

    private val commentRecycleList = mutableListOf<CommentModel>()
    private val adapter = AdpItmComment( commentRecycleList)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommentBinding.inflate(inflater, container, false)

        //setup Database
        val sqlDb = SqliteDatabase.getInstance(requireActivity())

        //create repository, factory, View Model
        val commentsRepo = CommentRepository(sqlDb.commentDAO)
        val factory = CommentViewModelFactory(commentsRepo)
        commentViewModel =  ViewModelProvider(requireActivity(), factory).get(CommentViewModel::class.java)

        binding.lifecycleOwner = this
        binding.currentViewModel = commentViewModel

        //setup back button pressed
        binding.frgComBtnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        commentViewModel.postId = arguments?.getInt("postId")!!
        commentViewModel.postInfo.value = arguments?.getString("postInfo")!!

        binding.frgComSearchVw.onActionViewCollapsed()
        binding.frgComSearchVw.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("Stanley", "Successful 1")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!= null){
                    commentViewModel.searchTxt = newText
                }
                resetRecycleView()
                Log.d("Stanley", "Successful 2")
                return false
            }
        })

        //Setup recycler view
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.frgComRcyVw.layoutManager = layoutManager
        binding.frgComRcyVw.addItemDecoration(DividerItemDecoration(binding.frgComRcyVw.context, layoutManager.orientation))
        binding.frgComRcyVw.adapter= adapter

        //observing data
        commentViewModel.commentLiveData.observe(requireActivity(),{ resetRecycleView() })
    }

    private fun resetRecycleView(){
        commentRecycleList.clear()
        val it =  commentViewModel.commentLiveData.value

        if (it != null) {
            Log.d("Stanley", commentViewModel.postId.toString())
            for (i in it) {
                if (i.postId == commentViewModel.postId &&(commentViewModel.searchTxt == "" || i.name.contains(commentViewModel.searchTxt) || i.email.contains(commentViewModel.searchTxt)|| i.body.contains(commentViewModel.searchTxt)) ) {
                    commentRecycleList.add(i)
                }
            }
        }
        binding.frgComRcyVw.adapter= adapter
    }
}