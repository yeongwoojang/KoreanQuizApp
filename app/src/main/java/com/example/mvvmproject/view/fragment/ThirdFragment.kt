package com.example.mvvmproject.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import com.example.mvvmproject.R
import kotlinx.android.synthetic.main.fragment_third.*
import javax.inject.Inject

class ThirdFragment : Fragment(R.layout.fragment_third) {
    companion object{
        val TAG : String = ThirdFragment::class.java.simpleName
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "$TAG :   onViewCreated: ")
        setFragmentResultListener("s_f_key"){
            requestKey, bundle -> val data = bundle.getString("carName","")
            sc_text.text = data
        }

    }
}