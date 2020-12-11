package com.example.mvvmproject.di.module

import android.content.Context
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.util.AddCookiesInterceptor
import com.example.mvvmproject.util.ReceivedCookiesInterceptor
import com.example.mvvmproject.util.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @CustomClient
    @Provides
    fun getCustomClient(@ApplicationContext appContext: Context) : ServiceAPI{
        val client = OkHttpClient().newBuilder()
        //쿠키를 sharedPreferences에 저장하고 가져온다.
            .addNetworkInterceptor(AddCookiesInterceptor(appContext))
            .addNetworkInterceptor(ReceivedCookiesInterceptor(appContext))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ServiceAPI::class.java)
    }



}