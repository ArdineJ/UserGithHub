package com.ardine.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardine.githubuser.adapter.FavoritUserAdapter
import com.ardine.githubuser.databinding.ActivityFavoriteBinding
import com.ardine.githubuser.model.FavoriteViewModel
import com.ardine.githubuser.model.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels{ ViewModelFactory.getInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllFavoriteUsers().observe( this){ listUser ->
            binding.rvUser.apply {
                layoutManager = LinearLayoutManager (this@FavoriteActivity)
                adapter = FavoritUserAdapter(listUser)
            }
        }
    }
}