package com.project.laundryapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.response.laundry.LaundryHistoryResponse
import com.project.laundryapp.databinding.ItemHistoryLaundryBinding

class LaundryHistoryAdapter : RecyclerView.Adapter<LaundryHistoryAdapter.LaundryHistoryResponseTopViewHolder>() {

    private var dataList = ArrayList<LaundryHistoryResponse>()
    var onItemClick: ((LaundryHistoryResponse) -> Unit)? = null

    fun setList(data: ArrayList<LaundryHistoryResponse>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryHistoryResponseTopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_laundry, parent, false)
        return LaundryHistoryResponseTopViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundryHistoryResponseTopViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class LaundryHistoryResponseTopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryLaundryBinding.bind(itemView)
        fun bind(data: LaundryHistoryResponse) {
            with(binding) {
                tvHistoryTitle.text = data.namaLaundry
                tvHistoryPrice.text = data.total
                tvHistoryDate.text = data.tglPesan
                tvHistoryStatus.text = data.status
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }
    }
}