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
    @CustomClient private val service: ServiceAPI
) :
    CoroutineWorker(context, workerParams) {
    val mContext = context

//    private val notificationManager =
//        mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val ioDispatchers = Dispatchers.IO

    var count = 0

    //백그라운드에서 동작
    @SuppressLint("SimpleDateFormat")
    override suspend fun doWork(): Result = coroutineScope {
//                val handler = Handler(Looper.getMainLooper())
//        withContext(Dispatchers.IO){
//            handler.postDelayed(Runnable { // Run your task here
//
//            }, 1000)
//            service.test()
//
//        }
        val request = OneTimeWorkRequestBuilder<TimeWorker>()
//            .setInitialDelay(1000L, TimeUnit.MILLISECONDS)
            .addTag("Time_Limit")
            .build()
        val response = withContext(ioDispatchers) {
//            //네트워크 처리
            service.getTest()
        }
        response?.run {
            //네트워크 응답처리
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm")

            val currentCal = Calendar.getInstance()
            val dueCal = Calendar.getInstance()
            Log.d("time", "doWork: $this.limit_time")


            dueCal.time = this.limit_time
            if (dueCal.after(currentCal)) {
                val response = withContext(ioDispatchers) {
                    service.restartIncorrectCount()
                }
                //지정한 제한시간이 지나면 백그라운드 요청 중지
            }else{
                delay(1000L)
                WorkManager.getInstance(applicationContext).enqueue(request)
            }
        } ?: run {
            //아무 일 도 없을 때
            Toast.makeText(mContext, "Wait", Toast.LENGTH_SHORT).show()
            Log.d("아무일도...", "없어")
        }

        Result.success()

    }

//    private fun createForegroundInfo() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val impotrance = NotificationManager.IMPORTANCE_LOW
//            val channel = NotificationChannel("TEST", "TestChannel", impotrance)
//            channel.description = "test_Channel"
//            notificationManager.createNotificationChannel(channel)
//        }
////        val notification = NotificationCompat
////            .Builder(applicationContext, "TEST")
////            .setSmallIcon(R.drawable.notification_icon_background)
////            .setContentTitle("ForegroundWorker")
////            .setContentText("In Progress ...")
////            .setOngoing(true)
////            // cancel action도 쉽게 추가할 수 있다. 📍
////            .addAction(android.R.drawable.ic_delete, "Cancel",
////                WorkManager.getInstance(applicationContext)
////                    .createCancelPendingIntent(id)
////            )
////            .build()
////        return ForegroundInfo(0, notification)
//    }
//
//    private fun showNodification(progress: Int) {
//        val notification = NotificationCompat
//            .Builder(applicationContext, "TEST")
//            .setSmallIcon(R.drawable.notification_icon_background)
//            .setContentTitle("ForegroundWorker")
//            .setProgress(100, progress, false)
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setOngoing(true)
//            // cancel action도 쉽게 추가할 수 있다. 📍
////            .addAction(android.R.drawable.ic_delete, "Cancel",
////                WorkManager.getInstance(applicationContext)
////                    .createCancelPendingIntent(id)
////            )
//            .build()
//
//        notificationManager.notify(2, notification)
//    }

    fun getTimeUsingWorkRequest(): Long {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        dueDate.add(Calendar.SECOND, 5)
        return dueDate.timeInMillis - currentDate.timeInMillis
    }
}