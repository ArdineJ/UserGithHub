package com.ardine.githubuser.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ardine.githubuser.R
import com.ardine.githubuser.adapter.SectionPagerAdapter
import com.ardine.githubuser.data.local.DbModule
import com.ardine.githubuser.data.remote.response.DetailUserResponse
import com.ardine.githubuser.databinding.ActivityDetailBinding
import com.ardine.githubuser.model.DetailViewModel
import com.ardine.githubuser.model.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TABS_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding:ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this, ViewModelFactory(DbModule(this)))[DetailViewModel::class.java]

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username!= null){
            detailViewModel.findUser(username)
        }

        detailViewModel.detailUser.observe(this){ item ->
           setDataUser(item)
        }

        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionPagerAdapter(this,username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.detailTabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TABS_TITLE[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val btnFavorite: FloatingActionButton = binding.btnFavorite
        btnFavorite.setOnClickListener {
            val newFavoriteStatus = !isFavorite

            if (newFavoriteStatus) {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.ic_heart_pink
                    )
                )
            } else {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.ic_heart_black
                    )
                )
            }

            isFavorite = newFavoriteStatus
        }

        TODO("Favorite")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDataUser(item: DetailUserResponse) {
        binding.apply {
            Glide.with(applicationContext)
                .load(item.avatarUrl)
                .error(R.drawable.baseline_person_24)
                .into(imgProfileUser)
            tvUserFullName.text = item.name
            tvUsername.text = item.login
            userFollowersCount.text = item.followers.toString()
            userFollowingCount.text = item.following.toString()
        }
    }

}