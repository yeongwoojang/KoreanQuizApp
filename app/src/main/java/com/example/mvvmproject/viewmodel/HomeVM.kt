package com.example.mvvmproject.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.model.vo.UsersQuizInfo
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.util.AlarmBroadcastReceiver
import com.example.mvvmproject.util.TimeWorker
import com.example.mvvmproject.viewmodel.KoreanQuizVM.Companion.ALARM_CALL_ACTION
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class HomeVM @ViewModelInject constructor(
    application: Application,
    @CustomClient private val service: ServiceAPI
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val usersQuizLiveData = MutableLiveData<UsersQuizInfo>()
    val incorrectCountLiveData = MutableLiveData<Int>()
    val putIncorrectCntResLv = MutableLiveData<Int>()


    val workManager = WorkManager.getInstance(context)



    val request = OneTimeWorkRequestBuilder<TimeWorker>()
//        .setInitialDelay(1000L, TimeUnit.MILLISECONDS)
        .addTag("Time_Limit")
        .build()
    val workInfoLiveData: LiveData<WorkInfo> = workManager.getWorkInfoByIdLiveData(request.id)


    init {
        viewUpdate()
        getIncorrectCount()
    }

     fun startWork(){
        workManager.enqueue(request)
    }

    private fun viewUpdate() {
        viewModelScope.launch {
            val usersQuizInfo = service.updateView()
            usersQuizLiveData.value = usersQuizInfo
        }
    }


    fun getIncorrectCount() {
        viewModelScope.launch {
            val incorrectCntInfo = service.getIncorrectCount().incorrectCount
            incorrectCountLiveData.value = incorrectCntInfo
            if (incorrectCountLiveData.value == 3) {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.MINUTE, 1)


                val alarmIntent = Intent(context, AlarmBroadcastReceiver::class.java)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    alarmIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )

                if (Build.VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                    if (Build.VERSION.SDK_INT >= 21) {
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            calendar.timeInMillis,
                            pendingIntent
                        )
                    } else {
                        alarmManager.set(
                            AlarmManager.RTC_WAKEUP,
                            calendar.timeInMillis,
                            pendingIntent
                        )
                    }
                }
            }
        }
    }

    fun restartIncorrectCount() {
        viewModelScope.launch {
            val putZeroCntRes = service.restartIncorrectCount().toInt()
            putIncorrectCntResLv.value = putZeroCntRes
        }
    }
    fun putLimitTime() {
        val dueDate = Calendar.getInstance()
        dueDate.add(Calendar.MINUTE, 10)
        viewModelScope.launch {
            val res = service.putTimeLimit()
        }
    }

}
