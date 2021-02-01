package com.project.laundryapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.response.laundry.LaundryDataResponse
import com.project.laundryapp.core.data.remote.response.laundry.LaundryOrderInput
import com.project.laundryapp.databinding.ItemLaundryOrderBinding

class LaundryOrderAdapter : RecyclerView.Adapter<LaundryOrderAdapter.LaundryOrderViewHolder>(){

    private var dataList = ArrayList<LaundryOrderInput>()
    var onItemClick: ((LaundryOrderInput) -> Unit)? = null

    fun setList(data: ArrayList<LaundryOrderInput>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    inner class LaundryOrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryOrderBinding.bind(itemView)

        fun bind(data: LaundryOrderInput) {
            with(binding) {
                val price = data.harga ?: 0
                val qty = data.jumlah ?: 0
                val totalPrice = price * qty
                val orderCount = "$qty Kg x Rp $price"

                tvOrderServiceName.text = data.idLayanan
                tvOrderTotalPrice.text = totalPrice.toString()
                tvOrderCount.text = orderCount
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryOrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laundry_order, parent, false)
        return LaundryOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundryOrderViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

}