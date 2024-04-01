package com.example.api

import com.example.api.models.ResponseCategory
import com.example.api.models.ResponseProduct
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

interface FoodAPI {
    @GET("search.php?s")
    suspend fun getAllProducts() : Result<ResponseProduct>

    @GET("categories.php")
    suspend fun getAllCategory() : Result<ResponseCategory>
}

fun FoodAPI(
    baseUrl : String,
    okHttpClient: OkHttpClient?
) : FoodAPI {
    return retrofit(baseUrl, okHttpClient).create()
}

fun retrofit(
    baseUrl : String,
    okHttpClient: OkHttpClient?
) : Retrofit {
    val modifiedOkHttpClient =
        (okHttpClient?.newBuilder() ?: OkHttpClient.Builder()).build()

    val json = Json { ignoreUnknownKeys = true }
    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .addConverterFactory(jsonConverterFactory)
        .client(modifiedOkHttpClient)
        .build()
}