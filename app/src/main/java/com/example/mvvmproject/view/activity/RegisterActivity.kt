package com.example.mvvmproject.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.mvvmproject.R
import com.example.mvvmproject.viewmodel.RegisterVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    
    companion object{
        val TAG = this::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        
        val viewModel by viewModels<RegisterVM>()
        register_bt.setOnClickListener {
            viewModel.register(
                rg_name_txt.text.toString(),
                rg_id_txt.text.toString(),
                rg_pw_txt.text.toString(),
                rg_phone_txt.text.toString()
            )
            viewModel.apply {
                loginAndRegsCode.observe(this@RegisterActivity, Observer { code->
                    if(code==200){
                        Toast.makeText(this@RegisterActivity,"회원가입 되었습니다.",Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Log.d(TAG, "register : 실패")
                    }

                })
            }
        }

        go_to_main_text.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}