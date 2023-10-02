package com.ardine.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ardine.githubuser.data.local.entity.FavoriteUser
import com.ardine.githubuser.data.local.room.FavoriteUserDao
import com.ardine.githubuser.data.local.room.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.FavoriteUserDao()
    }

    fun getAllUser(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getFavoriteUser()

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute{mFavoriteUserDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute{mFavoriteUserDao.delete(favoriteUser)}
    }

    fun update(favoriteUser: FavoriteUser){
        executorService.execute{mFavoriteUserDao.update(favoriteUser)}
    }
}