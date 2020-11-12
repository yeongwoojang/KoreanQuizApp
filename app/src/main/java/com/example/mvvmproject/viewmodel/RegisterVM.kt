package com.example.mvvmproject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.repository.ServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterVM @ViewModelInject constructor(
    @CustomClient private val service : ServiceAPI
) : ViewModel(){
    companion object{
        val TAG = this::class.java.simpleName
    }
    val loginAndRegsCode = MutableLiveData<Int>()

    fun register(name :String,id : String, pw : String ,phone : String){
        viewModelScope.launch(Dispatchers.IO) {
            val mRegisterCode = service.register(name,id,pw,phone)

            loginAndRegsCode.postValue(mRegisterCode.code)
        }
    }

    fun login(id : String, pw : String){
        viewModelScope.launch(Dispatchers.IO) {
            val mLoginCode = service.login(id,pw)

            loginAndRegsCode.postValue(mLoginCode.code)
        }
    }
}