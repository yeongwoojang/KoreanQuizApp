package com.example.mvvmproject.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ActivityQuizBinding
import com.example.mvvmproject.view.dialog.CompleteDialog
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_quiz.*

@AndroidEntryPoint
class QuizActivity : AppCompatActivity(){

    companion object {
        val TAG = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_quiz)
       val binding = DataBindingUtil.setContentView<ActivityQuizBinding>(this,R.layout.activity_quiz)

        val navHostFragment  = Navigation.findNavController(this,R.id.nav_host)


        binding.lifecycleOwner = this
        val viewModel by viewModels<KoreanQuizVM>()

        binding.viewModel = viewModel

//        viewModel.completeLiveData.observe(this@QuizActivity, Observer {complete->
//            if(complete){
//                val dialog= CompleteDialog.getInstance(yesClick = {yesClick->
//                    if(yesClick) {
//                        viewModel.updateScore()
////                        viewModel.goToNextQuiz()
//                    }
//                })
//                dialog.setCancelable(false)
//                dialog.show(supportFragmentManager,"NoticeDialogFragment")
//            }
//        })
//        viewModel.updateResponseLiveData.observe(this@QuizActivity, Observer {
//            if (it=="200") viewModel.viewUpdate()
//        })

//        viewModel.usersQuizLiveData.observe(this@QuizActivity, Observer {
//            if(it.score==1000){
//
//            }
//        })
        home_bt.setOnClickListener {
            finish()
        }
    }

}