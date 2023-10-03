package com.ardine.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ardine.githubuser.R
import com.ardine.githubuser.adapter.SectionPagerAdapter
import com.ardine.githubuser.data.local.entity.FavoriteUser
import com.ardine.githubuser.data.remote.response.DetailUserResponse
import com.ardine.githubuser.databinding.ActivityDetailBinding
import com.ardine.githubuser.model.DetailViewModel
import com.ardine.githubuser.model.FavoriteViewModel
import com.ardine.githubuser.model.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private val favoriteViewModel: FavoriteViewModel by viewModels{ ViewModelFactory.getInstance(application) }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val btnFavorite: FloatingActionButton = binding.btnFavorite

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username!= null){
            detailViewModel.findUser(username)
        }

        detailViewModel.isfailed.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }

        detailViewModel.detailUser.observe(this){ item ->
           setDataUser(item)
            if (username != null) {
                favoriteViewModel.getClickedUser(username).observe(this) { user ->
                    if (user != null) {
                        isFavorite = true
                        btnFavorite.setImageResource(R.drawable.ic_heart_pink)
                    } else {
                        isFavorite = false
                        btnFavorite.setImageResource(R.drawable.ic_heart_black_outline)
                    }
                }

                binding.btnShare.setOnClickListener {
                    val share = Intent(Intent.ACTION_SEND)
                    share.type = "text/plain"
                    share.putExtra(Intent.EXTRA_TEXT, item.htmlUrl)
                    startActivity(Intent.createChooser(share, "Share"))
                }

                btnFavorite.setOnClickListener {
                    isFavorite = !isFavorite

                    if (isFavorite) {
                        val favoriteUser = FavoriteUser(
                            username = username,
                            avatarUrl = item.avatarUrl,
                        )

                        favoriteViewModel.insertFavoriteUser(favoriteUser)
                        btnFavorite.setImageResource(R.drawable.ic_heart_pink)
                        Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
                    } else {
                        favoriteViewModel.getClickedUser(username).observe(this) { user ->
                            if (user != null) {
                                favoriteViewModel.deleteFavoriteUser(user)
                                btnFavorite.setImageResource(R.drawable.ic_heart_black_outline)
                                Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
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
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TABS_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}