package com.example.mvvmproject.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.di.qualifier.OpenAPIClient
import com.example.mvvmproject.model.vo.UsersQuizInfo
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.util.SharedPreference
import kotlinx.coroutines.launch

class HomeVM @ViewModelInject constructor(
    @CustomClient private val service: ServiceAPI
) : ViewModel() {

    val usersQuizLiveData = MutableLiveData<UsersQuizInfo>()

    init {
        viewUpdate()
    }

    private fun viewUpdate() {
        viewModelScope.launch {
            val usersQuizInfo = service.updateView()
            usersQuizLiveData.value = usersQuizInfo
        }
    }
}