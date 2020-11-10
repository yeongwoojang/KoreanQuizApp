package com.example.mvvmproject.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ActivityQuizBinding
import com.example.mvvmproject.view.adapter.QuizRecyAdt
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_quiz.*
import org.json.JSONArray

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    companion object {
        val TAG = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding = DataBindingUtil.setContentView<ActivityQuizBinding>(this,R.layout.activity_quiz)

        binding.lifecycleOwner = this
        val viewModel by viewModels<KoreanQuizVM>()
        binding.viewModel = viewModel
//        val adapter = QuizRecyAdt()
//        recyclerview.apply {
//            this.layoutManager =
//                LinearLayoutManager(this@QuizActivity, RecyclerView.VERTICAL, false)
//            this.adapter = adapter
//        }

//        viewModel.apply {
//            curQzDateLiveData.observe(this@QuizActivity, Observer {
//                if (it.code == 200) {
//                    val jsonArray2 = JSONArray(it.jsonArray)
//                    Log.d(TAG, "test: ${jsonArray2.getJSONObject(0).getString("_DATE")}")
//                    getQuizList(jsonArray2.getJSONObject(0).getString("_DATE"))
//                }
//                quizLiveData.observe(this@QuizActivity, Observer { rows ->
////                    adapter.updateItems(rows)
//                    Log.d(TAG, "test: ${rows.toString()}")
//                })
//            })
//        }

    }
}