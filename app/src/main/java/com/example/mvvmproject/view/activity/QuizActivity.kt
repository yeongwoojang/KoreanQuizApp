package com.example.mvvmproject.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ActivityQuizBinding
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_quiz.*


@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    companion object {
        val TAG = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
//       val binding = DataBindingUtil.setContentView<ActivityQuizBinding>(this,R.layout.activity_quiz)

//        binding.lifecycleOwner = this
        val viewModel by viewModels<KoreanQuizVM>()
//        binding.viewModel = viewModel


        home_bt.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.right_in,R.anim.left_out);
            finish()
        }
        viewModel.usersQuizLiveData.observe(this, Observer { usersQuizInfo ->
            if ((usersQuizInfo.quizSeq / 4 + 1)==101){
                quiz_no.text = "완료"
                quiz_text.text=" "
            }
            else {
                quiz_no.text = (usersQuizInfo.quizSeq / 4 + 1).toString()
            }
            score_text.text = usersQuizInfo.score.toString()
        })
    }

}