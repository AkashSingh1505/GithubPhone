package com.akma.githubusersearch.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.akma.githubusersearch.R
import com.akma.githubusersearch.data.UserSearchResponseItem
import com.bumptech.glide.Glide

class UserSearchResultAdapter(private val navController: NavController) :
    PagingDataAdapter<UserSearchResponseItem, UserSearchResultAdapter.UserViewHolder>(USER_COMPARATOR) {

    companion object {
        val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserSearchResponseItem>() {
            override fun areItemsTheSame(oldItem: UserSearchResponseItem, newItem: UserSearchResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserSearchResponseItem, newItem: UserSearchResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        val username: TextView = itemView.findViewById(R.id.username)
        val bio: TextView = itemView.findViewById(R.id.bio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_search_result, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.username.text = user!!.login

        // Load profile image using Glide or Picasso
        Glide.with(holder.profileImage.context)
            .load(user.avatar_url)
            .into(holder.profileImage)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("username", user.login) // Pass username to the ProfileFragment
            }
            navController.navigate(R.id.action_navigation_search_to_navigation_profile, bundle)
        }
    }

}
