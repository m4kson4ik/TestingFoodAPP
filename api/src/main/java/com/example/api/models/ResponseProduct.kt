package com.example.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseProduct(
    @SerialName("meals") val meals: List<ProductDTO>
)