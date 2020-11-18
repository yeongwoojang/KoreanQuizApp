package com.example.mvvmproject.di.module

import android.content.Context
import com.example.mvvmproject.util.HashSecurity
import com.example.mvvmproject.util.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object SecurityModule {
    @Provides
    fun getSecurityObj() : HashSecurity {
        return HashSecurity()
    }
}