package com.ardine.githubuser.model

import androidx.lifecycle.ViewModelProvider
import com.ardine.githubuser.data.local.DbModule

class ViewModelFactory(private val dbModule: DbModule) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
//            return DetailViewModel(dbModule) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
}
