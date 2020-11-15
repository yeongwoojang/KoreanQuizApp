package com.example.mvvmproject.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.example.mvvmproject.R
import com.example.mvvmproject.viewmodel.RegisterVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val viewModel by viewModels<RegisterVM>()


        menu_bt.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        nav_view.setNavigationItemSelectedListener { menuItem: MenuItem ->
            menuItem.setChecked(true)
            drawer_layout.closeDrawers()
            var id = menuItem.itemId
            when (id) {
                R.id.ranking -> Log.d(TAG, "nav_click : RankingMenu is Clicked")
                R.id.logout -> {
                    viewModel.removeCookies()
                    val intent  = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> Log.d(TAG, "onCreate: ")
            }
            true
        }


    }


}