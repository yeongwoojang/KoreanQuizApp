package com.example.mvvmproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.model.vo.RankRow
import com.example.mvvmproject.repository.ServiceAPI
import kotlinx.coroutines.launch

class RankingVM @ViewModelInject constructor(
    @CustomClient private val service: ServiceAPI
) : ViewModel() {

    val itemLiveData = MutableLiveData<List<RankRow>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    companion object {
        val TAG = RankingVM::class.java.simpleName
    }


    fun getRankingInfo() {
        loadingLiveData.value = true

        viewModelScope.launch {
            val rankInfo = service.getRanking().rankJsonArray
            itemLiveData.value = rankInfo
        }
        loadingLiveData.value = false

    }
}