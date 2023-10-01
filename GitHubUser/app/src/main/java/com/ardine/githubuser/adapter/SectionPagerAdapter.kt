package com.ardine.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardine.githubuser.ui.FollowersFragment
import com.ardine.githubuser.ui.FollowingsFragment

class SectionPagerAdapter(activity: AppCompatActivity,private val username:String?): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                username?.let {
                    (fragment as FollowersFragment).arguments = Bundle().apply {
                        putString(FollowersFragment.ARG_USERNAME, it)
                    }
                }
            }
            1 -> {
                fragment = FollowingsFragment()
                username?.let {
                    fragment.arguments = Bundle().apply {
                        putString(FollowingsFragment.ARG_USERNAME, it)
                }
            }
        }
    }
        return fragment as Fragment
    }
}