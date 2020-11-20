package com.example.mvvmproject.view.activity

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.mvvmproject.R
import kotlinx.android.synthetic.main.loading_activity.*

class LoadingActivity(context : Context) :Dialog(context){
    init{

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.let{
            it.setBackgroundDrawableResource(R.drawable.style_registerpage)
            it.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        }
        setCanceledOnTouchOutside(false)
        val animation : Animation = AnimationUtils.loadAnimation(context,R.anim.loading)
        loading_page.animation = animation
    }
    override fun show(){
        super.show()
    }
    override fun dismiss() {
        super.dismiss()
    }

}