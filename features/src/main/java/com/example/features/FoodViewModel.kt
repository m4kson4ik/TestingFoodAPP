package com.example.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestResult
import com.example.features.models.CategoryUI
import com.example.features.models.ProductUI
import com.example.features.usecase.GetAllCategoriesUseCase
import com.example.features.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val getAllCategoriesUseCase : Provider<GetAllCategoriesUseCase>,
    private val getAllProductsUseCase : Provider<GetAllProductsUseCase>,
) : ViewModel() {
    val products : StateFlow<State<ProductUI>> = getAllProductsUseCase.get().invoke().map { it.toState() }.stateIn(viewModelScope, SharingStarted.Lazily, State.None())
    val categories : StateFlow<State<CategoryUI>> = getAllCategoriesUseCase.get().invoke().map { it.toState() }.stateIn(viewModelScope, SharingStarted.Lazily, State.None())

}

private fun <T> RequestResult<List<T>>.toState(): State<T> {
    return when (this) {
        is RequestResult.Error -> State.Error(data)
        is RequestResult.InProgress -> State.Loading(data)
        is RequestResult.Success -> State.Success(data)
    }
}

sealed class State<T>(val data: List<T>?) {
    data class None<T>(val list: List<T>? = null) : State<T>(list)
    class Loading<T>(list: List<T>? = null) : State<T>(list)
    class Error<T>(list: List<T>? = null) : State<T>(list)
    class Success<T>(list: List<T>) : State<T>(list)
}