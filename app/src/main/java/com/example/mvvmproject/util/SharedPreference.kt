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

    var userId: String?
        get() = prefs.getString("loginInfo", null)
        set(value) = prefs.edit().putString("loginInfo", value).apply()

    fun removeId(context: Context, key: String) {
        prefs.edit().remove(key).apply()
    }

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