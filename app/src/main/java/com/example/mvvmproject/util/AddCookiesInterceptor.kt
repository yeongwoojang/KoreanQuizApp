package com.example.mvvmproject.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mvvmproject.App
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class AddCookiesInterceptor(context : Context) : Interceptor {

    val prefs: SharedPreference = SharedPreference(context)
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val cookies = prefs.getCookies()

        for (cookie: String in cookies!!) {
            builder.addHeader("Cookie", cookie)
        }

        //Web,Android,IOS 구분을 위해 User-Agent셋팅
        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android")

        return chain.proceed(builder.build())
    }
}