package com.example.mvvmproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.model.vo.IdCheckRes
import com.example.mvvmproject.model.vo.RegsRes
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.util.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterVM @ViewModelInject constructor(
    @CustomClient private val service : ServiceAPI,
    private val pref : SharedPreference
) : ViewModel(){
    companion object{
        val TAG = this::class.java.simpleName
    }
    val loginAndRegsCode = MutableLiveData<RegsRes>()
    val idChkCode = MutableLiveData<RegsRes>()

    fun register(name :String,id : String, pw : String ,phone : String){
        viewModelScope.launch(Dispatchers.IO) {
            val mRegisterCode = service.register(name,id,pw,phone)

            loginAndRegsCode.postValue(mRegisterCode)
        }
    }
    fun idChk(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            val mIdChkCode = service.idChk(id)
            idChkCode.postValue(mIdChkCode)
        }
    }

    fun login(id : String, pw : String){
        viewModelScope.launch(Dispatchers.IO) {
            val mLoginCode = service.login(id,pw)
            loginAndRegsCode.postValue(mLoginCode)
        }
    }
    fun test(data : String){
        pref.userId = data
        Log.d(TAG, "test: ${pref.userId}")
    }

    fun getLoginSession() : String?{
        return pref.userId
    }

    fun remove(context : Context,data : String){
        pref.removeId(context,data)
    }
}