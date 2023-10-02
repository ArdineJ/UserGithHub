package com.ardine.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardine.githubuser.R
import com.ardine.githubuser.data.local.entity.FavoriteUser
import com.ardine.githubuser.databinding.ItemUserBinding
import com.ardine.githubuser.ui.DetailActivity
import com.bumptech.glide.Glide

class FavoritUserAdapter(private val listUser: List<FavoriteUser>) : RecyclerView.Adapter<FavoritUserAdapter.ViewHolder>() {

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
                tvUsername.text = users.username
                Glide.with(itemView.context)
                    .load(users.avatarUrl)
                    .error(R.drawable.baseline_person_24)
                    .into(binding.userAvatar)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, users.username)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }


}
