package com.example.mvvmproject.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmBroadcastReciever : BroadcastReceiver() {

    companion object {
        val ALARM_CALL_ACTION = "alarmCallAction";

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(ALARM_CALL_ACTION))
            Toast.makeText(context, "sadfsadf", Toast.LENGTH_SHORT).show()

    }
}