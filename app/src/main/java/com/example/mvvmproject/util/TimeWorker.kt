package com.example.mvvmproject.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.*
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.viewmodel.RegisterVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class TimeWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @CustomClient private val service: ServiceAPI,
    private val prefs: SharedPreference
) :
    CoroutineWorker(context, workerParams) {
    val mContext = context

    private val ioDispatchers = Dispatchers.IO

    //백그라운드에서 동작
    @SuppressLint("SimpleDateFormat")
    override suspend fun doWork(): Result = coroutineScope {
        var outputData = Data.Builder().putBoolean("output", false)
        val request = OneTimeWorkRequestBuilder<TimeWorker>()
//            .setInitialDelay(1000L, TimeUnit.MILLISECONDS)
            .addTag("Time_Limit2")
            .build()
        var userSession: String = " "
        val iterator = prefs.getCookies()?.iterator()
        if (iterator != null) {
            while (iterator.hasNext()) {
                userSession = iterator.next()
                userSession = userSession.split(";")[0].split("=")[1]
            }
        }
        Log.d("USER_ID", "doWork: $userSession")
        if (userSession == " ") {
            Log.d("TEST", "doWork: OUT")
            outputData.build()
//            WorkManager.getInstance()?.cancelAllWorkByTag(userSession)
        }else{

            val response = withContext(ioDispatchers) {
//            //네트워크 처리
                service.getTimeLimit()
            }
            Log.d("TEST", "doWork: IN")
            response?.run {
                //네트워크 응답처리
                if(this.limit_time!=null){
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    val currentCal = Calendar.getInstance()
                    val dueCal = Calendar.getInstance()
                    val tz = TimeZone.getDefault()
                    val mill = this.limit_time.time
                    val offset = tz.getOffset(mill)

                    var utcTime = sdf.format(mill - offset)
                    utcTime = utcTime.replace("+0000", "")
                    dueCal.time = sdf.parse(utcTime)

                    if (currentCal.after(dueCal)) {
                        Log.d("TAG", "doWork: TRUE")
                        val response = withContext(ioDispatchers) {
                            service.restartIncorrectCount()
                        }
                        response?.run {
                            if (this == "200") service.deleteTimeLimit()

                            outputData.putBoolean("output", true)
                        }
                        //지정한 제한시간이 지나면 백그라운드 요청 중지
                    } else {
                        Log.d("TAG", "doWork: FALSE")
                        delay(1000L)
                        outputData.putBoolean("output", false)
                        WorkManager.getInstance(applicationContext).enqueue(request)
                    }
                }
            } ?: run {
                //아무 일 도 없을 때
                Log.d("아무일도...", "없어")
            }
        }


        Result.success(outputData.build())

    }
}