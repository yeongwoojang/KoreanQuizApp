package com.example.mvvmproject.di.module

import android.content.Context
import com.example.mvvmproject.util.AddCookiesInterceptor
import com.example.mvvmproject.util.ReceivedCookiesInterceptor
import com.example.mvvmproject.util.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient

@Module
@InstallIn(ApplicationComponent::class)
object SharedPrefModule {
    @Provides
    fun getSharedPref(@ApplicationContext appContext: Context) : SharedPreference{
        return SharedPreference(appContext)
    }

//    @Provides
//    fun getAddCookiesInterceptor(@ApplicationContext appContext : Context) : AddCookiesInterceptor{
//        return AddCookiesInterceptor(appContext)
//    }
//
//    @Provides
//    fun getReceivedCookiesInterceptor(@ApplicationContext appContext : Context) : ReceivedCookiesInterceptor{
//        return ReceivedCookiesInterceptor(appContext)
//    }

}