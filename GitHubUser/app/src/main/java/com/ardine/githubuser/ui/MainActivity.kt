package com.ardine.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardine.githubuser.R
import com.ardine.githubuser.adapter.UserListAdapter
import com.ardine.githubuser.data.remote.response.User
import com.ardine.githubuser.databinding.ActivityMainBinding
import com.ardine.githubuser.model.MainViewModel
import com.ardine.githubuser.setting.SettingActivity
import com.ardine.githubuser.setting.SettingPreferences
import com.ardine.githubuser.setting.dataStore

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

        mainViewModel.isfailed.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
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

        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent = Intent(this,FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        val pref = SettingPreferences.getInstance(applicationContext.dataStore)

        pref.getThemeSetting().asLiveData().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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



