package com.example.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCategory(
    @SerialName("categories") val categories: List<CategoryDTO>
)