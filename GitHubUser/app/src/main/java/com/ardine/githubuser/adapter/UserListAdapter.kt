package com.ardine.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardine.githubuser.R
import com.ardine.githubuser.data.remote.response.User
import com.ardine.githubuser.databinding.ItemUserBinding
import com.ardine.githubuser.ui.DetailActivity
import com.bumptech.glide.Glide

class UserListAdapter(private val listUser: List<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = listUser[position]

        holder.apply {
            binding.apply {
                tvUsername.text = users.login
                Glide.with(itemView.context)
                    .load(users.avatarUrl)
                    .error(R.drawable.baseline_person_24)
                    .into(binding.userAvatar)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, users.login)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }


}
