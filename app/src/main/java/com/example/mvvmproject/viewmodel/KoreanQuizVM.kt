package com.example.mvvmproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.di.qualifier.OpenAPIClient
import com.example.mvvmproject.model.vo.QuizDate
import com.example.mvvmproject.model.vo.Row
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.view.activity.MainActivity
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class KoreanQuizVM @ViewModelInject constructor(
    @OpenAPIClient private val service: ServiceAPI,
    @CustomClient private val service2: ServiceAPI
) : ViewModel() {
    val quizLiveData = MutableLiveData<List<Row>>()
    val curQzDateLiveData = MutableLiveData<QuizDate>()
    var startCalandar = Calendar.getInstance()
    companion object {
        val TAG = this::class.simpleName
    }

    init {
        initCalandar()
        getCurQzDate()
    }

    fun getQuizList(curQzData: String) {
//        viewModelScope.launch {
//            val quizInfo = service.getKoreanQuize(curQzData)
//            quizLiveData.value = quizInfo.koreanAnswerInfo.row
//        }

        val sdf = SimpleDateFormat("yyyyMMdd")
        for(i in 1..365){
            startCalandar.add(Calendar.DATE,1)
            if(!(startCalandar.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY
                        || startCalandar.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY)){
                val data = sdf.format(startCalandar.time)
                Log.d(MainActivity.TAG, "calendar: $data")
            }
        }
    }

    fun getCurQzDate() {
        viewModelScope.launch {
                val curQzDate = service2.getCurrentDate()
                curQzDateLiveData.value = curQzDate
                val jsonArray = JSONArray(curQzDateLiveData.value?.jsonArray)
            getQuizList(jsonArray.getJSONObject(0).getString("_DATE"))
        }
    }
    fun initCalandar(){
        startCalandar.set(Calendar.YEAR,2013)
        startCalandar.set(Calendar.MONTH,1)
        startCalandar.set(Calendar.DATE,2)
    }
}