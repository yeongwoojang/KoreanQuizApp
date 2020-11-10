package com.example.mvvmproject.di.module

import com.example.mvvmproject.di.qualifier.OpenAPIClient
import com.example.mvvmproject.repository.ServiceAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object OpenAPIModule {

    val gson = GsonBuilder()
        .setLenient()
        .create()

    @OpenAPIClient
    @Provides
    fun getOpenApiClient() : ServiceAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(ServiceAPI.KOREAN_QUIZE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ServiceAPI::class.java)
    }
}