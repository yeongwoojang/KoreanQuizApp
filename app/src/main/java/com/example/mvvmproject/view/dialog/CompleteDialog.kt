package com.example.mvvmproject.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ActivityQuizBinding
import com.example.mvvmproject.databinding.DialogCompleteBinding
import com.example.mvvmproject.viewmodel.KoreanQuizVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_complete.*
import kotlinx.android.synthetic.main.dialog_complete.view.*
import java.lang.IllegalStateException

@AndroidEntryPoint
class CompleteDialog(private val yesClick: (Boolean) -> Unit) : DialogFragment() {
    val viewModel by activityViewModels<KoreanQuizVM>()
    companion object {
        fun getInstance(yesClick : (Boolean) -> Unit): DialogFragment {
            return CompleteDialog(yesClick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.form_complete_dialog);
        val view = inflater.inflate(R.layout.dialog_complete, container, false)

        view.next_quiz.setOnClickListener {
            yesClick(true)
            dismiss()
        }
        return view
    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    override fun dismiss() {
        super.dismiss()
    }

}