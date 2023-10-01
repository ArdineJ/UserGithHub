package com.ardine.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "FavoriteUser")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    val id:Int?,
    @ColumnInfo(name = "username")
    var username: String = "",
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,
    @ColumnInfo(name = "followers_url")
    val followersUrl: String?,
    @ColumnInfo(name = "following_url")
    val followingUrl: String?
):Parcelable