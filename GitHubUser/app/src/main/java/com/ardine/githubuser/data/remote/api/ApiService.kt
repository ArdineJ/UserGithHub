package com.ardine.githubuser.data.remote.api

import com.ardine.githubuser.data.local.entity.User
import com.ardine.githubuser.data.remote.response.DetailUserResponse
import com.ardine.githubuser.data.remote.response.GithhubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<GithhubResponse>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowings(
        @Path("username") username: String?
    ): Call<List<User>>
}