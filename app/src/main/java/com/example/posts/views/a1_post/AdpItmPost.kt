package com.example.posts.views.a1_post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.posts.R
import com.example.posts.model.PostModel

class AdpItmPost (private var postList: MutableList<PostModel>, private val listener: OnItemClickListener)
    : RecyclerView.Adapter<AdpItmPost.ViewHolder>(){

    // need to be inner class, to get the listener
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val txtVw = itemView.findViewById<TextView>(R.id.itm_txtVw)
        init {
            txtVw.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            listener.onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itm_txtvw, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempString = //"ID: ${postList[position].id}\n" +
               // "User ID: ${postList[position].userId}\n\n" +
                "Title: ${postList[position].title}\n\n" +
                "Text: ${postList[position].body}"
        holder.txtVw.text = tempString
    }

    override fun getItemCount(): Int = postList.size

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}