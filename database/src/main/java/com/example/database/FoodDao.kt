package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.models.CategoryDBO
import com.example.database.models.ProductDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * from categories")
    suspend fun getAllCategories() : List<CategoryDBO>

    @Query("SELECT * from categories")
    fun observeAllCategories() : Flow<List<CategoryDBO>>
    @Insert
    suspend fun insertCategory(items : List<CategoryDBO>)

    @Query("DELETE FROM categories")
    suspend fun cleanCategory()



    @Query("SELECT * from products")
    suspend fun getAllProducts() : List<ProductDBO>

    @Query("SELECT * from products")
    fun observeAllProducts() : Flow<List<ProductDBO>>
    @Insert
    suspend fun insertProducts(items : List<ProductDBO>)

    @Query("DELETE FROM products")
    suspend fun cleanProducts()
}