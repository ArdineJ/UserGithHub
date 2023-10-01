package com.ardine.githubuser.data.local

import android.content.Context
import androidx.room.Room
import com.ardine.githubuser.data.local.room.FavoriteUserDatabase

class DbModule (private val context: Context) {
    private val db = Room.databaseBuilder(context, FavoriteUserDatabase::class.java, "usergithub.db")
        .allowMainThreadQueries()
        .build()
    val userDao = db.FavoriteUserDao()
}