package com.example.mvvmproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmproject.R
import com.example.mvvmproject.databinding.ItemRankingBinding
import com.example.mvvmproject.model.vo.RankRow
import java.util.ArrayList

class RankingViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView){
    val binding = ItemRankingBinding.bind(itemView)
}
class RankingAdapter : RecyclerView.Adapter<RankingViewHolder>() {
    private var mItems: List<RankRow> = ArrayList<RankRow>()

    fun updateItems(items: List<RankRow>) {
        mItems = items
        notifyDataSetChanged() //UI 갱신
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RankingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RankingViewHolder,
        position: Int
    ) {
        val ranking :RankRow =mItems[position]
        holder.binding.rank = mItems[position]
}

    override fun getItemCount(): Int {
        return mItems.size
    }
}



