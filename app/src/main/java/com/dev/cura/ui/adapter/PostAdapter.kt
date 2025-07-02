package com.dev.cura.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.cura.R
import com.dev.cura.data.model.PostDto
import com.dev.cura.util.CircleTransform
import com.squareup.picasso.Picasso

class PostAdapter(private val posts: List<PostDto>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postImage: ImageView = itemView.findViewById(R.id.postImage)
        val postCaption: TextView = itemView.findViewById(R.id.postCaption)
        val userImage: ImageView = itemView.findViewById(R.id.postUserProfileImage)
        val likeIcon: ImageView = itemView.findViewById(R.id.likeIcon) // Like button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        // Bind post image
        post.imageUrl?.let {
            Picasso.get().load(it).into(holder.postImage)
        }

        // Bind user profile image
        post.userImageUrl?.let {
            Picasso.get().load(it).transform(CircleTransform()).into(holder.userImage)
        }

        // Set initial drawable based on liked status
        if (post.liked == true) {
            holder.likeIcon.setImageResource(R.drawable.ic_heart)
        } else {
            holder.likeIcon.setImageResource(R.drawable.ic_heart_hollow)
        }

        // Handle Like button click
        holder.likeIcon.setOnClickListener {
            val isLiked = post.liked == true
            post.liked = !isLiked // Toggle the liked status
            holder.likeIcon.setImageResource(if (post.liked == true) R.drawable.ic_heart else R.drawable.ic_heart_hollow)

        }
        // Bind other fields
        holder.postCaption.text = post.caption
    }

    override fun getItemCount(): Int = posts.size
}

