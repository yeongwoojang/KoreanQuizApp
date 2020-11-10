package com.example.mvvmproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.di.qualifier.OpenAPIClient
import com.example.mvvmproject.model.vo.*
import com.example.mvvmproject.repository.ServiceAPI
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
   @CustomClient private val service : ServiceAPI
): ViewModel() {
    val TAG : String = "MainViewModel"

    val userLiveData = MutableLiveData<User>()
    val regiResLiveData = MutableLiveData<RegiRes>()
//    private val service: ServiceAPI = RetrofitService().getClient().create(ServiceAPI::class.java)




    fun regisration(id: String, pw: String): Unit {
        Log.d(TAG,"regisration")
        viewModelScope.launch {
       val regiRes = service.register(id,pw)
            regiResLiveData.value = regiRes
        }
    }
}