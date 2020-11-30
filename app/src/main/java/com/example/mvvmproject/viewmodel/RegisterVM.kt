package com.example.mvvmproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.model.vo.RegsRes
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.util.HashSecurity
import com.example.mvvmproject.util.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterVM @ViewModelInject constructor(
    @CustomClient private val service: ServiceAPI,
    private val prefs: SharedPreference,
    private val security: HashSecurity
) : ViewModel() {
    companion object {
        val TAG = this::class.java.simpleName
    }

    val loginAndRegsCode = MutableLiveData<RegsRes>()
    val idChkCode = MutableLiveData<RegsRes>()
    val sessionOkLiveData = MutableLiveData<String>()

    fun register(name: String, id: String, pw: String, phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mRegisterCode = service.register(name, id, pw, phone)

            loginAndRegsCode.postValue(mRegisterCode)
        }
    }



    fun idChk(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mIdChkCode = service.idChk(id)
            idChkCode.postValue(mIdChkCode)
        }
    }

    fun login(id: String, pw: String) {
        Log.d(TAG, "HASHCODE: ${security.sha256(id)}")
        Log.d(TAG, "HASHCODE: ${security.sha256(security.sha256(id))}")
        viewModelScope.launch(Dispatchers.IO) {
            val mLoginCode = service.login(id, pw)
            loginAndRegsCode.postValue(mLoginCode)
        }
    }

    //    fun test(data : String){
//        App.prefs.userId = data
//        Log.d(TAG, "test: ${App.prefs.userId}")
//    }
//
//
//    fun getLoginSession() : String?{
//        return App.prefs.userId
//    }
    fun getLoginSession(): String {
        var userSession: String = " "
        val iterator = prefs.getCookies()?.iterator()
        if (iterator != null) {
            while (iterator.hasNext()) {
                userSession = iterator.next()
                userSession = userSession.split(";")[0].split("=")[1]
                Log.d(TAG, "getLoginSession: $userSession")
            }
        }
        return userSession

    }


    fun removeCookies() {
        prefs.removeCookies()
    }
}