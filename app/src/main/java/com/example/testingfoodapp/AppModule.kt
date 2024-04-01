package com.example.testingfoodapp

import android.content.Context
import com.example.api.FoodAPI
import com.example.database.FoodDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideHttpClient() : OkHttpClient? {
        if(BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }
        return null
    }

    @Provides
    @Singleton
    fun provideFoodAPI(okHttpClient: OkHttpClient?) : FoodAPI {
        return FoodAPI(
            baseUrl = BuildConfig.API_BASE_URL,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideFoodDataBase(@ApplicationContext context: Context) : FoodDataBase {
        return FoodDataBase(context)
    }
}