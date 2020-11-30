package com.example.mvvmproject.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmproject.R
import com.example.mvvmproject.view.adapter.RankingAdapter
import com.example.mvvmproject.viewmodel.RankingVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_ranking.*

@AndroidEntryPoint
class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<RankingVM>()
        setContentView(R.layout.activity_ranking)

        viewModel.getRankingInfo()

        val adapter = RankingAdapter()

        recyclerView.apply{
            this.layoutManager = LinearLayoutManager(this@RankingActivity, RecyclerView.VERTICAL,false)
            this.adapter = adapter
        }
        home_bt.setOnClickListener {
            val intent = Intent(this@RankingActivity,HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.right_in,R.anim.left_out);
            finish()
        }
        viewModel.apply {
            this.itemLiveData.observe(this@RankingActivity, Observer {
                adapter.updateItems(it)
            })
        }

    }


}