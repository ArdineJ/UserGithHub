package com.ardine.githubuser.model

import com.ardine.githubuser.data.remote.api.ApiConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardine.githubuser.data.remote.response.GithhubResponse
import com.ardine.githubuser.data.remote.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers : LiveData<List<User>> = _listUsers

    private val _isfailed = MutableLiveData<String>()
    val isfailed : LiveData<String> = _isfailed
    init {
        searchUser(USERNAME)
    }

    internal fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object :Callback<GithhubResponse>{
            override fun onResponse(
                call: Call<GithhubResponse>,
                response: Response<GithhubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null){
                    _listUsers.value = response.body()?.items?.filterNotNull() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<GithhubResponse>, t: Throwable) {
                _isLoading.value = false
                t.message?.let{Log.d("failure", it)}
                _isfailed.value = "Failed to Load Data"
            }
        })
    }

    companion object{
        private const val USERNAME = "ArdineJ"
    }
}
