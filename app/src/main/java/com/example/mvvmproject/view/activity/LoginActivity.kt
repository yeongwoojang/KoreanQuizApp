package com.example.mvvmproject.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.mvvmproject.R
import com.example.mvvmproject.viewmodel.RegisterVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel by viewModels<RegisterVM>()

        login_bt.setOnClickListener {
            val id = id_txt.text.toString().trim()
            val pw = pw_txt.text.toString().trim()
            if(id!="" && pw!=""){
            viewModel.login(id_txt.text.toString(),pw_txt.text.toString())
            }else{
                Toast.makeText(this@LoginActivity,"정확한 정보를 입력하세요",Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.apply {
            loginAndRegsCode.observe(this@LoginActivity, Observer {response->
                if(response.code==200){
                    viewModel.test(id_txt.text.toString().trim())
                    val intent = Intent(this@LoginActivity,QuizActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity,response.message,Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity,response.message,Toast.LENGTH_SHORT).show()
                }
            })
        }


    }
}