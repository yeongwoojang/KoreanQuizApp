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

    companion object {
        val TAG = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val viewModel by viewModels<RegisterVM>()

        id_chk_bt.setOnClickListener {
            val rgNameTxt = rg_id_txt.text.toString().trim()

            if (rgNameTxt != "") {
                viewModel.idChk(rgNameTxt)
            } else Toast.makeText(this@RegisterActivity, "값을 정확히 입력하세요", Toast.LENGTH_SHORT).show()
        }

        register_bt.setOnClickListener {
            val rgNameTxt = rg_name_txt.text.toString().trim()
            val rgIdTxt = rg_id_txt.text.toString().trim()
            val rgPwTxt = rg_pw_txt.text.toString().trim()
            val rgPhoneTxt = rg_phone_txt.text.toString().trim()

            if (rgNameTxt != "" && rgIdTxt != "" && rgPwTxt != "" && rgPhoneTxt != "") {
                viewModel.register(
                    rg_name_txt.text.toString(),
                    rg_id_txt.text.toString(),
                    rg_pw_txt.text.toString(),
                    rg_phone_txt.text.toString()
                )
            } else {
                Toast.makeText(this@RegisterActivity, "값을 정확히 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.apply {
            loginAndRegsCode.observe(this@RegisterActivity, Observer { response ->
                Log.d(TAG, response.code.toString())
                if (response.code == 200) {
                    Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    //code == 404
                    Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
        viewModel.idChkCode.observe(this@RegisterActivity, Observer { response ->
            if (response.code == 200) {
                Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_LONG).show()
            } else {
                //code == 404
                Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_LONG).show()
            }
        })

        go_to_main_text.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}