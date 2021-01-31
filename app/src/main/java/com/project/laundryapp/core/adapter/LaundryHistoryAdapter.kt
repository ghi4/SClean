package com.project.laundryapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.local.LaundryOrder
import com.project.laundryapp.databinding.ItemHistoryLaundryBinding
import com.project.laundryapp.databinding.ItemLaundryOrderBinding

class LaundryHistoryAdapter : RecyclerView.Adapter<LaundryHistoryAdapter.LaundryOrderTopViewHolder>() {

    private var dataList = ArrayList<LaundryOrder>()
    var onItemClick: ((LaundryOrder) -> Unit)? = null

    fun setList(data: ArrayList<LaundryOrder>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryOrderTopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_laundry, parent, false)
        return LaundryOrderTopViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundryOrderTopViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class LaundryOrderTopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryLaundryBinding.bind(itemView)
        fun bind(data: LaundryOrder) {
            with(binding) {
                tvHistoryTitle.text = "Laundry A"
                tvHistoryPrice.text = "Rp. 20.000"
                tvHistoryDate.text = "Tanggal: 11/01/2021"
                tvHistoryStatus.text = "Unknown"
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }
    }
}