package com.example.features.usecase

import com.example.data.FoodRepository
import com.example.data.RequestResult
import com.example.data.map
import com.example.features.models.CategoryUI
import com.example.features.models.toCategoryUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke() : Flow<RequestResult<List<CategoryUI>>> {
        return repository.getDataCategories().map { it.map { it.map { it.toCategoryUI() } } }
    }
}