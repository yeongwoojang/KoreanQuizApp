package com.example.mvvmproject.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.example.mvvmproject.R
import com.example.mvvmproject.viewmodel.HomeVM
import com.example.mvvmproject.viewmodel.RegisterVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    val intent2 = getIntent()
    val userViewModel by viewModels<HomeVM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var canStart = true



        val viewModel by viewModels<RegisterVM>()


        val userId = viewModel.getLoginSession()
        user_id.text = "${userId} 님 환영합니다."
        userViewModel.usersQuizLiveData.observe(this, Observer {
            if (it.code == 200) {
                user_score.text = if (it.score != 1000) "현재 점수는 ${it.score}점 입니다."
                else "모든 문제를 다 푸셨습니다."
            }
        })
        menu_bt.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        nav_view.setNavigationItemSelectedListener { menuItem: MenuItem ->
            menuItem.setChecked(true)
            drawer_layout.closeDrawers()
            var id = menuItem.itemId
            when (id) {
                R.id.ranking -> {
                }
                R.id.logout -> {
                    viewModel.removeCookies()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> Log.d(TAG, "onCreate: ")
            }
            true
        }
        viewModel.sessionOkLiveData.observe(this@HomeActivity, Observer {
            if (it == "200") {
                Log.d(TAG, "onCreate: ${it}")
            }
        })

        userViewModel.putIncorrectCntResLv.observe(this, Observer {
            if (it == 200) {
                userViewModel.getIncorrectCount()
            }
        })

        userViewModel.incorrectCountLiveData.observe(this, Observer {
            if (it == 3) {
//                canStart = false
//                userViewModel.startLongTask()
            }
            else canStart = true
        })


        start_bt.setOnClickListener {
            if (canStart) {
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            } else Toast.makeText(this, "30분 후에 문제를 풀 수 있습니다.", Toast.LENGTH_SHORT).show()
        }

    }

}