package com.example.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("categories")
data class CategoryDBO(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
) {
    @PrimaryKey(true) var uid : Int = 0
}
