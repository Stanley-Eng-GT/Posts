package com.example.posts.views.a2_comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.posts.R
import com.example.posts.model.CommentModel


class AdpItmComment(private var commentList: MutableList<CommentModel>)
    : RecyclerView.Adapter<AdpItmComment.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtVw = itemView.findViewById<TextView>(R.id.itm_txtVw)!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itm_txtvw, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempString = //"ID: ${commentList[position].id}\n" +
                //"Post ID: ${commentList[position].postId}\n\n" +
                "Name: ${commentList[position].name}\n" +
                "Email: ${commentList[position].email}\n\n" +
                "Comment: ${commentList[position].body}"
        holder.txtVw.text = tempString
    }

    override fun getItemCount(): Int = commentList.size

}