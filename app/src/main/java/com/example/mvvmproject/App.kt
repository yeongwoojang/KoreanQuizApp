package com.example.mvvmproject

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}




