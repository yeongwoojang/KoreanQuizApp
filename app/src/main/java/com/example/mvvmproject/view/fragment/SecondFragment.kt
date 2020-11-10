package com.example.mvvmproject.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.mvvmproject.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_second.*
import javax.inject.Inject

class SecondFragment : Fragment(R.layout.fragment_second) {

    companion object{
        val TAG : String = SecondFragment::class.java.simpleName
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "$TAG :   onViewCreated: ")

        setFragmentResultListener("f_f_key"){
            requestKey, bundle -> val result = bundle.getString("carName","")
            s_text.text = result
        }

        s_bt.setOnClickListener {
            setFragmentResult("s_f_key", bundleOf("carName" to "fgdfg"))
            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
        }
    }
}