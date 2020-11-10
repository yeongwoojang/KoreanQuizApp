package com.example.mvvmproject.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvvmproject.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
    val TAG: String = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR,2013)
        calendar.set(Calendar.MONTH,1)
        calendar.set(Calendar.DATE,2)
        val sdf = SimpleDateFormat("yyyyMMdd")
        for(i in 1..365){
            calendar.add(Calendar.DATE,1)
            if(!(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
                || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)){
            val data = sdf.format(calendar.time)
            Log.d(TAG, "calendar: $data")
            }
        }

        start_bt.setOnClickListener {
            val intent = Intent(this,QuizActivity::class.java)
            startActivity(intent)

            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }
    }
}