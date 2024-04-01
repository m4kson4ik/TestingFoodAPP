package com.example.data

import android.util.Log
import com.example.api.FoodAPI
import com.example.api.models.CategoryDTO
import com.example.api.models.ProductDTO
import com.example.api.models.ResponseCategory
import com.example.api.models.ResponseProduct
import com.example.data.merge.MergeStrategy
import com.example.data.merge.RequestResponseMergeStrategy
import com.example.data.models.Category
import com.example.data.models.Product
import com.example.database.FoodDataBase
import com.example.database.models.CategoryDBO
import com.example.database.models.ProductDBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val db : FoodDataBase,
    private val api : FoodAPI
) {
    fun getDataProducts(
        mergeStrategy: MergeStrategy<RequestResult<List<Product>>> = RequestResponseMergeStrategy(),
        ) : Flow<RequestResult<List<Product>>> {
        val dataApi = getAllProductsInAPI()
        val dataDb = getAllProductsInDataBase()
        return dataDb.combine(dataApi, mergeStrategy::merge).flatMapLatest { res ->
            if (res is RequestResult.Success) {
                db.foodDao.observeAllProducts()
                    .map { it.map { it.toProduct() } }
                    .map { RequestResult.Success(it) }
            } else {
                flowOf(res)
            }
        }
    }

    fun getDataCategories(
        mergeStrategy: MergeStrategy<RequestResult<List<Category>>> = RequestResponseMergeStrategy(),
        ) : Flow<RequestResult<List<Category>>>{
        val dataApi = getAllCategoriesInAPI()
        val dataDb = getAllCategoriesInDataBase()
        return dataDb.combine(dataApi, mergeStrategy::merge).flatMapLatest { res ->
            if (res is RequestResult.Success) {
                db.foodDao.observeAllCategories()
                    .map { it.map { it.toCategories() } }
                    .map { RequestResult.Success(it) }
            } else {
                flowOf(res)
            }
        }
    }


    private fun getAllProductsInAPI() : Flow<RequestResult<List<Product>>> {
        val requestResult = flow {
            emit(api.getAllProducts())
        }.onEach { res ->
            if (res.isSuccess) {
                savedProductsInDataBase(res.getOrThrow().meals)
            }
        }.onEach {res-> if (res.isFailure) { Log.d("mgkit", res.toString() )} }
            .map { it.toRequestResult() }
        val start = flowOf<RequestResult<ResponseProduct>>(RequestResult.InProgress())
        return merge(requestResult, start).map { res : RequestResult<ResponseProduct> ->
            res.map { it.meals.map { it.toProduct() } }
        }
    }

    private fun getAllCategoriesInAPI() : Flow<RequestResult<List<Category>>> {
        val requestResult = flow {
            emit(api.getAllCategory())
        }.onEach { res ->
            if (res.isSuccess) {
                savedCategoriesInDataBase(res.getOrThrow().categories)
            }
        }.onEach {res-> if (res.isFailure) { Log.d("mgkit", res.toString() )} }
            .map { it.toRequestResult() }
        val start = flowOf<RequestResult<ResponseCategory>>(RequestResult.InProgress())
        return merge(requestResult, start).map { res : RequestResult<ResponseCategory> ->
            res.map { it.categories.map { it.toCategories() } }
        }
    }

    private fun getAllProductsInDataBase() : Flow<RequestResult<List<Product>>> {
        val dbRequest = db.foodDao::getAllProducts.asFlow()
            .map<List<ProductDBO>, RequestResult<List<ProductDBO>>> { RequestResult.Success(it) }
            .catch {
                emit(RequestResult.Error(null, it))
            }
        val start = flowOf<RequestResult<List<ProductDBO>>>(RequestResult.InProgress())
        return merge(dbRequest, start).map { res -> res.map { it.map { it.toProduct() } } }
    }

    private fun getAllCategoriesInDataBase() : Flow<RequestResult<List<Category>>> {
        val dbRequest = db.foodDao::getAllCategories.asFlow()
            .map<List<CategoryDBO>, RequestResult<List<CategoryDBO>>> { RequestResult.Success(it) }
            .catch {
                emit(RequestResult.Error(null, it))
            }
        val start = flowOf<RequestResult<List<CategoryDBO>>>(RequestResult.InProgress())
        return merge(dbRequest, start).map { res -> res.map { it.map { it.toCategories() } } }
    }

    private suspend fun savedCategoriesInDataBase(data : List<CategoryDTO>) {
        val dbos = data.map { it.toCategoriesDBO() }
        db.foodDao.cleanCategory()
        db.foodDao.insertCategory(dbos)
    }

    private suspend fun savedProductsInDataBase(data : List<ProductDTO>) {
        val dbos = data.map { it.toProductDBO() }
        db.foodDao.cleanProducts()
        db.foodDao.insertProducts(dbos)
    }
}