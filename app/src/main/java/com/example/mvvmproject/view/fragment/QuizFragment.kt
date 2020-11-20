package com.example.mvvmproject.view.fragment

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.FragmentQuizBinding
import com.example.mvvmproject.view.activity.LoadingActivity
import com.example.mvvmproject.view.dialog.CompleteDialog
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.util.*


@AndroidEntryPoint
class QuizFragment : Fragment() {
    val viewModel by activityViewModels<KoreanQuizVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customLoading = LoadingActivity(requireContext())
        customLoading.show()
        viewModel.loadingLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it ==true){
                customLoading.dismiss()
                quiz_screen.visibility=View.VISIBLE
            }
        })
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
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = CompleteDialog.getInstance(yesClick = {yesClick->
            if(yesClick){
                viewModel.viewUpdate()
            }

        })
        //정답을 선택했을 시 팝업 표시
        viewModel.completeLiveData.observe(requireActivity(), androidx.lifecycle.Observer  { complete->
            if(complete){
                viewModel.updateScore()
                dialog.isCancelable = false
                dialog.show(childFragmentManager,"NoticeDialogFragment")
            }
        })

        //DB에 점수와 문제 번호가 잘 업데이트 되었다면
        //UI를 업데이트 하기위해 DB를 읽어와서 화면을 갱신해준다.
//        viewModel.updateResponseLiveData.observe(requireActivity(), androidx.lifecycle.Observer  {
//            if (it=="200") viewModel.viewUpdate()
//        })

        //모든 문제를 다 풀었다면 완료화면 프래그먼트로 교체한다.
        viewModel.usersQuizLiveData.observe(requireActivity(), androidx.lifecycle.Observer  {
            if(it.score==1000){
                findNavController().navigate(R.id.action_quizFragment_to_completeFragment)
//                Log.d("1000cpom", "onViewCreated: 완료")
            }
        })
    }

}