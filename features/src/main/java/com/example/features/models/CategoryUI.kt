package com.example.features.models

import com.example.data.models.Category

data class CategoryUI(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)

fun Category.toCategoryUI() : CategoryUI {
    return CategoryUI(
        idCategory, strCategory, strCategoryDescription, strCategoryThumb
    )
}