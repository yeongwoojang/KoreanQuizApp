package com.example.mvvmproject.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.work.*
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.di.qualifier.OpenAPIClient
import com.example.mvvmproject.model.vo.IncorrectCount
import com.example.mvvmproject.model.vo.Row
import com.example.mvvmproject.model.vo.UsersQuizInfo
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.util.SharedPreference
import com.example.mvvmproject.util.TimeWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class KoreanQuizVM @ViewModelInject constructor(
    application: Application,
    @OpenAPIClient private val service: ServiceAPI,
    @CustomClient private val customService: ServiceAPI,
    private val prefs: SharedPreference
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val quizLiveData = MutableLiveData<List<Row>>()
    val completeLiveData = MutableLiveData<Boolean>()
    val updateResponseLiveData = MutableLiveData<String>()
    val usersQuizLiveData = MutableLiveData<UsersQuizInfo>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val incorrectCountLiveData = MutableLiveData<Int>()
    val putIncorrectCntResLv = MutableLiveData<Int>()



    companion object {
        val TAG = this::class.simpleName
        val ALARM_CALL_ACTION = "alarmCallAction";
    }


    init {
        getQuizList()
        viewUpdate()
        getIncorrectCount()

    }
    //퀴즈목록 받아오기
    private fun getQuizList() {
        var index = 0
        var qSeq = 0.0
        loadingLiveData.value = false
        viewModelScope.launch(Dispatchers.IO) {
            val quizInfo = service.getKoreanQuiz().koreanAnswerInfo.row.filter { row ->
                row.q_name.startsWith("다")
            }
            Log.d(TAG, "getQuizList: ${quizInfo.toString()}")
            for (i in quizInfo.indices) {
//                quizInfo[i].q_name = "다" + quizInfo[i].q_name
                if (quizInfo[i].q_seq != qSeq) {
                    index += 1
                    qSeq = quizInfo[i].q_seq
                    Log.d(TAG, "getQuizList: $qSeq")
                }
                quizInfo[i].quizNO = index
            }
            quizLiveData.postValue(quizInfo)
            loadingLiveData.postValue(true)

        }

    }


    //퀴즈를 맞췄는지 안맞췄는지 여부 알기
    fun isCorrectAnswer(answer: String) {
        var isCorrect = false
        for (i in 0..quizLiveData.value!!.size - 1) {
            if (quizLiveData.value!!.get(i).a_name.equals(answer)
                && quizLiveData.value!!.get(i).a_correct.equals("정답")
            ) {
                isCorrect = true
                break
            } else {
                isCorrect = false
            }
        }
        completeLiveData.value = isCorrect
    }

    fun updateScore() {
        viewModelScope.launch {
            updateResponseLiveData.value = customService.updateScore()
        }
    }

    fun viewUpdate() {
        viewModelScope.launch {
            val usersQuizInfo = customService.updateView()
            if (usersQuizInfo.quizSeq==0){
            }
            usersQuizLiveData.value = usersQuizInfo
        }
    }

    fun getIncorrectCount() {
        viewModelScope.launch {
            val incorrectCntInfo = customService.getIncorrectCount().incorrectCount
            incorrectCountLiveData.value = incorrectCntInfo
            if (incorrectCountLiveData.value == 3) {

            }
        }


    }

    fun putIncorrectCount() {
        viewModelScope.launch {
            val putIncorrectCntRes = customService.putIncorrectCount().toInt()
            putIncorrectCntResLv.value = putIncorrectCntRes
        }
    }

    fun restartIncorrectCnt() {
        viewModelScope.launch {
            val putZeroCntRes = customService.restartIncorrectCount().toInt()
            putIncorrectCntResLv.value = putZeroCntRes
        }

    }
}

