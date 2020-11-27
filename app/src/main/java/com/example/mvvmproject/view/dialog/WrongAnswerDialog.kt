package com.example.mvvmproject.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mvvmproject.R
import kotlinx.android.synthetic.main.dialog_wrong_answer.view.*

class WrongAnswerDialog(private val okClick: (Boolean) -> Unit) : DialogFragment() {

    companion object {
        fun getInstance(okClick: (Boolean) -> Unit): DialogFragment {
            return WrongAnswerDialog(okClick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.form_complete_dialog);
        val view = inflater.inflate(R.layout.dialog_wrong_answer, container, false)
        view.ok_bt.setOnClickListener {
            okClick(true)
            dismiss()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.20).toInt()
        dialog!!.window?.setLayout(width,height)
    }
}
