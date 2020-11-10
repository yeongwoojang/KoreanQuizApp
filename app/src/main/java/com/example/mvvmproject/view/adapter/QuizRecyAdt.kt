package com.example.mvvmproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.QuizItemBinding
import com.example.mvvmproject.model.vo.Row

class QuizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = QuizItemBinding.bind(itemView)

}

class QuizRecyAdt : RecyclerView.Adapter<QuizeViewHolder>() {

    private var mItems: List<Row> = ArrayList<Row>()

    fun updateItems(items: List<Row>) {
        mItems = items
        notifyDataSetChanged() //UI 갱신
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.quiz_item, parent, false)
        return QuizeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: QuizeViewHolder, position: Int) {
        val row: Row = mItems[position]
        holder.binding.quize = mItems[position]
    }
}

@BindingAdapter("Q_NAME")
fun setQ_Name(textView: TextView, row: Row) {
    textView.text = row.q_name
}

@BindingAdapter("A_NAME")
fun setA_Name(textView: TextView, row: Row) {
    textView.text = row.a_name
}




