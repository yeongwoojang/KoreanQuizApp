package com.example.mvvmproject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.di.qualifier.OpenAPIClient
import com.example.mvvmproject.model.vo.Row
import com.example.mvvmproject.model.vo.UsersQuizInfo
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.util.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KoreanQuizVM @ViewModelInject constructor(
    @OpenAPIClient private val service: ServiceAPI,
    @CustomClient private val customService: ServiceAPI,
    private val prefs: SharedPreference
) : ViewModel() {
    val quizLiveData = MutableLiveData<List<Row>>()

    val quizIndexLiveData = MutableLiveData<Int>()

    val quizNoLiveData = MutableLiveData<String>()

    val completeLiveData = MutableLiveData<Boolean>()

    val scoreLiveData = MutableLiveData<Int>()

    val updateResponseLiveData = MutableLiveData<String>()

    val usersQuizLiveData = MutableLiveData<UsersQuizInfo>()


    companion object {
        val TAG = this::class.simpleName
    }

    init {
        getQuizList()
        viewUpdate()
    }

    //퀴즈목록 받아오기
    private fun getQuizList() {
        var index = 0
        var qSeq = 0.0
//        quizIndexLiveData.value = 0
//        scoreLiveData.value = 0
        viewModelScope.launch(Dispatchers.IO) {
            val quizInfo = service.getKoreanQuiz().koreanAnswerInfo.row.filter { row ->
                row.q_name.startsWith("음")
            }
            for (i in quizInfo.indices) {
                quizInfo[i].q_name = "다" + quizInfo[i].q_name
                if (quizInfo[i].q_seq != qSeq) {
                    index += 1
                    qSeq = quizInfo[i].q_seq
                }
                quizInfo[i].quizNO = index
            }
            quizLiveData.postValue(quizInfo)
//            quizNoLiveData.postValue(quizInfo[0].quizNO.toString() + "번")
        }

    }

    //퀴즈를 맞췄는지 안맞췄는지 여부 알기
    fun isCorrectAnswer(answer: String) {
        completeLiveData.value = false
        for (i in 0..quizLiveData.value!!.size - 1) {
            if (quizLiveData.value!!.get(i).a_name.equals(answer)
                && quizLiveData.value!!.get(i).a_correct.equals("정답")
            ) {
                completeLiveData.value = true
//                updateScore()
            }
        }
    }

    //다음 문제로 이동
//    fun goToNextQuiz() {
//        quizIndexLiveData.value = quizIndexLiveData.value?.plus(4)
////        scoreLiveData.value = scoreLiveData.value?.plus(plusScore)
//        viewUpdate()
////        getQuizNo()
//    }
    


    //문제 번호 얻어오기
//    private fun getQuizNo() {
//        quizNoLiveData.value =
//            quizLiveData.value?.get(quizIndexLiveData.value!!)?.quizNO.toString() + "번"
//    }

    fun updateScore() {
        viewModelScope.launch {
            updateResponseLiveData.value = customService.updateScore()
        }
//        viewUpdate()
    }

     fun viewUpdate() {
        viewModelScope.launch {
            val usersQuizInfo = customService.updateView()
            usersQuizLiveData.value = usersQuizInfo
        }
    }
}