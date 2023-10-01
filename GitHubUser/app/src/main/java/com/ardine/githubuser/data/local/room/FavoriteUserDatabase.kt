package com.ardine.githubuser.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ardine.githubuser.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun FavoriteUserDao(): FavoriteUserDao

//    companion object{
//        @Volatile
//        private var INSTANCE: FavoriteUserDatabase? = null
//
//        @JvmStatic
//        fun getDatabase(context: Context): FavoriteUserDatabase {
//            if(INSTANCE == null){
//                synchronized(FavoriteUserDatabase::class.java){
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                        FavoriteUserDatabase::class.java, "favoritUser_database")
//                        .build()
//                }
//            }
//            return INSTANCE as FavoriteUserDatabase
//        }
//    }
    // TODO:
}