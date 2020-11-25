package com.example.mvvmproject.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.mvvmproject.di.qualifier.CustomClient
import com.example.mvvmproject.repository.ServiceAPI
import com.example.mvvmproject.view.activity.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Handler

@AndroidEntryPoint
class AlarmBroadcastReceiver(
    @CustomClient private val service : ServiceAPI
) : BroadcastReceiver() {

    companion object {
        val ALARM_CALL_ACTION = "alarmCallAction";
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(ALARM_CALL_ACTION)){
            Toast.makeText(context, "sadfsadf", Toast.LENGTH_SHORT).show()
        }

    }
    suspend fun restartIncorrectCount(){
        service.restartIncorrectCount()
    }
}