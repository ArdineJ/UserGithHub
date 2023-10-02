package com.ardine.githubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardine.githubuser.data.remote.api.ApiConfig
import com.ardine.githubuser.data.remote.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _followersUser = MutableLiveData<List<User>>()
    val followerUser:LiveData<List<User>> = _followersUser

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower:LiveData<Boolean> = _isLoadingFollower

    private val _isfailed = MutableLiveData<String>()
    val isfailed : LiveData<String> = _isfailed
    fun getFollowerUser(username:String?){
        _isLoadingFollower.value = true

        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<User>>{
            override    fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingFollower.value = false
                if(response.isSuccessful && response.body() != null) {
                    _followersUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFollower.value = false
                t.message?.let { Log.d("failure", it) }
                _isfailed.value = "Failed to Load Data"
            }
        })
    }
}