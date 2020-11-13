package com.example.mvvmproject.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ActivityQuizBinding
import com.example.mvvmproject.view.dialog.CompleteDialog
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_quiz.*
import org.json.JSONArray

@AndroidEntryPoint
class QuizActivity : AppCompatActivity(){

    companion object {
        val TAG = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding = DataBindingUtil.setContentView<ActivityQuizBinding>(this,R.layout.activity_quiz)

        binding.lifecycleOwner = this
        val viewModel by viewModels<KoreanQuizVM>()

        binding.viewModel = viewModel

        viewModel.completeLiveData.observe(this@QuizActivity, Observer {complete->
            if(complete){
                val dialog= CompleteDialog.getInstance(yesClick = {yesClick->
                    if(yesClick) {
                        viewModel.goToNextQuiz()
                    }
                })
                dialog.show(supportFragmentManager,"NoticeDialogFragment")
            }
        })
        back_bt.setOnClickListener {
            finish()
        }
    }


}