package com.example.mvvmproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.di.qualifier.OpenAPIClient
import com.example.mvvmproject.model.vo.Row
import com.example.mvvmproject.repository.ServiceAPI
import kotlinx.coroutines.launch

class KoreanQuizVM @ViewModelInject constructor(
    @OpenAPIClient private val service: ServiceAPI,
    @CustomClient private val service2: ServiceAPI
) : ViewModel() {
    val quizLiveData = MutableLiveData<List<Row>>()

    val quizIndexLiveData = MutableLiveData<Int>()

    val completeLiveData = MutableLiveData<Boolean>()

    val scoreLiveData = MutableLiveData<Int>()

    val plusScore = 10

    companion object {
        val TAG = this::class.simpleName
    }

    init {
        getQuizList()
    }
    private fun getQuizList() {
        quizIndexLiveData.value =0
        scoreLiveData.value = 0
        viewModelScope.launch {
            val quizInfo = service.getKoreanQuiz().koreanAnswerInfo.row.filter { row ->
                row.q_name.startsWith("음")
            }
            for (i in quizInfo.indices) {
                quizInfo[i].q_name = "다" + quizInfo[i].q_name
            }
            quizLiveData.value = quizInfo
            Log.d("sdafs", quizLiveData.value!!.size.toString())
        }
    }

    fun isCorrectAnswer(answer: String) {
        completeLiveData.value = false
        for (i in 0..quizLiveData.value!!.size - 1) {
            if (quizLiveData.value!!.get(i).a_name.equals(answer)
                && quizLiveData.value!!.get(i).a_correct.equals("정답")
            ) {
//                quizIndex.value = quizIndex.value?.plus(4)
                completeLiveData.value = true
            }
        }
    }

    fun goToNextQuiz(){
        quizIndexLiveData.value = quizIndexLiveData.value?.plus(4)
        scoreLiveData.value = scoreLiveData.value?.plus(plusScore)
    }

}