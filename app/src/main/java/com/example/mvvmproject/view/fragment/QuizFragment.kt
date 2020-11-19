package com.example.mvvmproject.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.FragmentQuizBinding
import com.example.mvvmproject.view.dialog.CompleteDialog
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*

@AndroidEntryPoint
class QuizFragment : Fragment() {
    val viewModel by viewModels<KoreanQuizVM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentQuizBinding>(
            inflater,
            R.layout.fragment_quiz,
            container,
            false
        )
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.completeLiveData.observe(requireActivity(), androidx.lifecycle.Observer  { complete->
            if(complete){
                val dialog= CompleteDialog.getInstance(yesClick = { yesClick->
                    if(yesClick) {
                        viewModel.updateScore()
//                        viewModel.goToNextQuiz()
                    }
                })
                dialog.setCancelable(false)
                dialog.show(childFragmentManager,"NoticeDialogFragment")
            }
        })
//
//        viewModel.updateResponseLiveData.observe(requireActivity(), androidx.lifecycle.Observer  {
//            if (it=="200") viewModel.viewUpdate()
//        })
//
//        viewModel.usersQuizLiveData.observe(requireActivity(), androidx.lifecycle.Observer  {
//            if(it.score==1000){
////                val action = QuizFr
////                findNavController().navigate(action)
//            }
//        })

    }
}