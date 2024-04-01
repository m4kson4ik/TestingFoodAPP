package com.example.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.models.CategoryDBO
import com.example.database.models.ProductDBO

class FoodDataBase internal constructor(private val db : FoodRoomDataBase) {
    val foodDao : FoodDao get() = db.getDao()
}

@Database(entities = [CategoryDBO::class, ProductDBO::class], version = 1)
internal abstract class FoodRoomDataBase () : RoomDatabase() {
    abstract fun getDao() : FoodDao
}

fun FoodDataBase(context : Context) : FoodDataBase {
    return FoodDataBase(Room.databaseBuilder(context, FoodRoomDataBase::class.java, "food_db").build())
}