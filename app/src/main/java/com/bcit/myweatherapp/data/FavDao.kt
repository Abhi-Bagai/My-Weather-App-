package com.bcit.myweatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavDao {
    @Query("SELECT * FROM favorite_table")
    fun getAll(): List<FavoriteCity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(city: FavoriteCity)

    @Delete
    fun remove(city: FavoriteCity)


}