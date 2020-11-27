package com.example.mvvmproject.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.FragmentQuizBinding
import com.example.mvvmproject.view.activity.HomeActivity
import com.example.mvvmproject.view.activity.LoadingActivity
import com.example.mvvmproject.view.dialog.CompleteDialog
import com.example.mvvmproject.view.dialog.WrongAnswerDialog
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_quiz.*


@AndroidEntryPoint
class QuizFragment : Fragment() {
    val viewModel by activityViewModels<KoreanQuizVM>()
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    val fragment = this.targetFragment
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
        val correctDialog = CompleteDialog.getInstance(yesClick = { yesClick ->
            if (yesClick) {
                viewModel.viewUpdate()
            }
        })
        val wrongDialog = WrongAnswerDialog.getInstance(okClick = {okClick->
            if(okClick){
                val intent = Intent(requireActivity(),HomeActivity::class.java)
                intent.putExtra("incorrectCount",3)
                startActivity(intent)
                requireActivity().finish()
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
                    correctDialog.isCancelable = false
                    correctDialog.show(childFragmentManager, "CorrectDialog")
                    viewModel.restartIncorrectCnt()
                } else {
                    viewModel.putIncorrectCount()
                }
            })

        //유저의 현재 점수를 초기화한다.
        viewModel.usersQuizLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            score = it.score
        })

        viewModel.putIncorrectCntResLv.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it == 200) {
                viewModel.getIncorrectCount()
            }
        })

        viewModel.incorrectCountLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            incorrect_cnt.text = "틀린 횟수 : ${it}번"
            if (it == 3) {
                wrongDialog.isCancelable =false
                wrongDialog.show(childFragmentManager,"WrongDialog")
            }else{

            }
        })


    }


}