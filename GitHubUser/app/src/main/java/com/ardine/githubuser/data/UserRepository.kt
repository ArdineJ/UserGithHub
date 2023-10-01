package com.ardine.githubuser.data

import androidx.lifecycle.MediatorLiveData
import com.ardine.githubuser.data.local.entity.User
import com.ardine.githubuser.data.local.room.FavoriteUserDao
import com.ardine.githubuser.data.remote.api.ApiService
import com.ardine.githubuser.utils.AppExecutors
import com.ardine.githubuser.utils.Result

class UserRepository private constructor(
    private val apiService:ApiService,
    private val usersDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<User>>>()

//    fun getUsers(username:String): LiveData<Result<List<User>>> {
//        result.value = Result.Loading
//        val client = ApiConfig.getApiService().getUsers(username)
//        client.enqueue(object : Callback<GithhubResponse> {
//            override fun onResponse(
//                call: Call<GithhubResponse>,
//                response: Response<GithhubResponse>
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<GithhubResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
//    }
}