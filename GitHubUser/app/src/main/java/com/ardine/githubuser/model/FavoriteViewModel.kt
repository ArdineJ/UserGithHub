package com.ardine.githubuser.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ardine.githubuser.data.local.entity.FavoriteUser
import com.ardine.githubuser.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getClickedUser(username: String): LiveData<FavoriteUser?> {
        return repository.getFavoriteUserByUsername(username)
    }
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return repository.getAllUser()
    }

    fun insertFavoriteUser(favoriteUser: FavoriteUser) {
        repository.insert(favoriteUser)
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        repository.delete(favoriteUser)
    }
}
