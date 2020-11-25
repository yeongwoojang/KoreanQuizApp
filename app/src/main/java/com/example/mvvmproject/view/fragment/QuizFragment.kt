package com.example.mvvmproject.view.fragment

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
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
    var score = 0
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
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //정답을 맞췄을 시 화면을 갱신해준다.
        val dialog = CompleteDialog.getInstance(yesClick = { yesClick ->
            if (yesClick) {
                viewModel.viewUpdate()
            }
        })

        //로딩화면을 띄운다.
        val customLoading = LoadingActivity(requireContext())
        customLoading.show()

        //퀴즈 로딩이 다되었는지 관찰하고 그 후 작업 수행
        viewModel.loadingLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            //퀴즈를 다 불러왔을 때 풀 문제가 남아있다면 퀴즈를 보여준다.
            if (it == true && score != 1000) {
                customLoading.dismiss()
                quiz_screen.visibility = View.VISIBLE
                //퀴즈를 다 불러왔을 때 문제를 다 풀었다면 완료 프래그먼트로 교체한다.
            } else if (it == true && score == 1000) {
                customLoading.dismiss()
                findNavController().navigate(R.id.action_quizFragment_to_completeFragment)
            }
        })

        //정답을 선택했을 시 팝업 표시
        viewModel.completeLiveData.observe(
            requireActivity(),
            androidx.lifecycle.Observer { complete ->
                if (complete) {
                    viewModel.updateScore()
                    dialog.isCancelable = false
                    dialog.show(childFragmentManager, "NoticeDialogFragment")
                    viewModel.restartIncorrectCnt()
                }else{
                    viewModel.putIncorrectCount()
                }
            })

        //유저의 현재 점수를 초기화한다.
        viewModel.usersQuizLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            score = it.score
        })

        viewModel.putIncorrectCntResLv.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it==200){
                viewModel.getIncorrectCount()
            }
        })

        viewModel.incorrectCountLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            incorrect_cnt.text = "틀린 횟수 : ${it}번"
            if (it==3){
                viewModel.startLongTask()
                oneBt.isClickable = false
                twoBt.isClickable = false
                threeBt.isClickable = false
                fourBt.isClickable = false

            }else{
                oneBt.isClickable = true
                twoBt.isClickable = true
                threeBt.isClickable = true
                fourBt.isClickable = true
            }
        })
        viewModel.workInfoLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            workInfo ->
            if(workInfo.state== WorkInfo.State.SUCCEEDED){
                Toast.makeText(requireContext(),"success!!",Toast.LENGTH_SHORT).show()
            }
        })
    }

}