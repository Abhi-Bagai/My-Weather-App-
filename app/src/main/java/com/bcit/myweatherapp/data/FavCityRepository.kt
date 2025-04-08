package com.bcit.myweatherapp.data

import android.database.sqlite.SQLiteConstraintException

class FavCityRepository (private val favDao: FavDao) {

    suspend fun insertEntity(city:FavoriteCity){
        try {
            favDao.add(city)
        } catch (e: SQLiteConstraintException) {
            // Handle duplicate
        }


    }

    fun getAll() : List<FavoriteCity>{
        return favDao.getAll()
    }

    fun remove(city: FavoriteCity){
        favDao.remove(city)
    }




}