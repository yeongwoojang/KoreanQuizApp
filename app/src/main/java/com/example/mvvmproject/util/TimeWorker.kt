package com.example.mvvmproject.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.repository.ServiceAPI
import kotlinx.coroutines.*


class TimeWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @CustomClient private val service: ServiceAPI
) :
    CoroutineWorker(context, workerParams) {
    val mContext = context

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 1000L
    }

    var count = 0
    //백그라운드에서 동작
    override suspend fun doWork(): Result = coroutineScope {
        for (i in 0..5) {
            val test = service.test().toInt()
            delay(delayDuration)
//        val count2 = service.getIncorrectCount().incorrectCount
//        count = count2
        }
        Result.success()

    }
}