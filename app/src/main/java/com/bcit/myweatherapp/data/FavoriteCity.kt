package com.bcit.myweatherapp.data



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "favorite_table",
    indices = [Index(value = ["city_name"], unique = true)]
)
class FavoriteCity (
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "city_name") val cityName: String?,

)

