package com.example.mvvmproject.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreference(context : Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE)

    var userId : String?
        get() = prefs.getString("loginInfo",null)
        set(value) = prefs.edit().putString("loginInfo",value).apply()

    fun removeId(context : Context, key :String){
        prefs.edit().remove(key).apply()
    }
}