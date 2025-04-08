package com.bcit.myweatherapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteCity::class], version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favDao(): FavDao
}

//singleton pattern
object MyDatabase{
    fun getDatabase(context: Context): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "fav_db")
            .fallbackToDestructiveMigration() // wipes and recreates the DB on schema change
            .allowMainThreadQueries()
            .build()
    }
}