package com.akma.githubusersearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akma.githubusersearch.R
import com.akma.githubusersearch.data.Repos
import com.akma.githubusersearch.data.UserSearchResponseItem
import com.bumptech.glide.Glide

class UserRepositoryAdapter(private var repoList: Repos) :
    RecyclerView.Adapter<UserRepositoryAdapter.UserRepositoryViewHolder>() {

    class UserRepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val repoNameTextView: TextView = view.findViewById(R.id.repoNameTextView)
        private val repoDescriptionTextView: TextView = view.findViewById(R.id.repoDescriptionTextView)
        private val repoStarsTextView: TextView = view.findViewById(R.id.repoStarsTextView)
        private val repoForkCountTextView: TextView = view.findViewById(R.id.repoForkCountTextView)

        fun bind(repo: Repos.RepoItem) {
            repoNameTextView.text = repo.name
            repoDescriptionTextView.text = repo.description
            repoStarsTextView.text = "Stars: ${repo.stargazers_count}"
            repoForkCountTextView.text = "Forks: ${repo.forks}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return UserRepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserRepositoryViewHolder, position: Int) {
        val repo = repoList!![position]
        holder.bind(repo)

    }


    override fun getItemCount(): Int = repoList!!.size
}
