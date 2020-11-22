package com.example.mvvmproject.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.mvvmproject.App
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class SharedPreference(context: Context) {

    val prefs: SharedPreferences =
        context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE)

    fun putCookies(cookies: HashSet<String>) {
        prefs.edit().putStringSet("cookies", cookies).apply()
    }

    fun getCookies(): MutableSet<String>? {
        return prefs.getStringSet("cookies", HashSet<String>())
    }

    fun remoceCookies(){
        prefs.edit().remove("cookies").apply()
    }
}