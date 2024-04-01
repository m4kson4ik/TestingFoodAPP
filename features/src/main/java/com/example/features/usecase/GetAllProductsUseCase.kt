package com.example.features.usecase

import com.example.data.FoodRepository
import com.example.data.RequestResult
import com.example.data.map
import com.example.features.models.ProductUI
import com.example.features.models.toProductUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke() : Flow<RequestResult<List<ProductUI>>> {
        return repository.getDataProducts()
            .map { req-> req.map { it.map { it.toProductUI() } } }
    }
}