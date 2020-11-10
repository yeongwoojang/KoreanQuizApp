package com.example.mvvmproject.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mvvmproject.R
//import com.example.mvvmproject.viewmodel.KoreanQuizeVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment(R.layout.fragment_first) {



    companion object{
        val TAG  = this::class.java.simpleName
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_bt.setOnClickListener {
            setFragmentResult("f_f_key", bundleOf("carName" to "Sfg"))
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }
}
