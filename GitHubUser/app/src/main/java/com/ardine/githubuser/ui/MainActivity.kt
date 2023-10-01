package com.ardine.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardine.githubuser.adapter.UserListAdapter
import com.ardine.githubuser.data.local.entity.User
import com.ardine.githubuser.databinding.ActivityMainBinding
import com.ardine.githubuser.model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.listUsers.observe(this){items->
            binding.rvUser.adapter = showRecyclerView(items)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    val query = searchBar.text.toString()
                    mainViewModel.searchUser(query)
                    searchView.hide()
                    false
                }
        }
    }

    private fun showRecyclerView(list: List<User>?): UserListAdapter {
        val userList = ArrayList<User>()
        list?.let{
            userList.addAll(it)
        }
        return UserListAdapter(userList)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}



