package com.example.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    @SerialName("idCategory") val idCategory: String,
    @SerialName("strCategory") val strCategory: String,
    @SerialName("strCategoryDescription") val strCategoryDescription: String,
    @SerialName("strCategoryThumb") val strCategoryThumb: String
)