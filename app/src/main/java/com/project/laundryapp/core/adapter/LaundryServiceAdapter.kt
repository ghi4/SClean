package com.project.laundryapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.response.LaundryServiceResponse
import com.project.laundryapp.databinding.ItemLaundryOrderBinding
import com.project.laundryapp.databinding.ItemLaundryServiceBinding

class LaundryServiceAdapter : RecyclerView.Adapter<LaundryServiceAdapter.LaundryServiceViewHolder>() {

    private var dataList = ArrayList<LaundryServiceResponse>()
    var onItemClick: ((LaundryServiceResponse) -> Unit)? = null

    fun setList(data: ArrayList<LaundryServiceResponse>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laundry_service, parent, false)
        return LaundryServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundryServiceViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class LaundryServiceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryServiceBinding.bind(itemView)
        fun bind(data: LaundryServiceResponse) {
            with(binding) {
                val estimasi = "(Estimasi ${data.estimasi} hari)"
                val price = "Rp. ${data.harga}"
                var count = 0
                tvServiceName.text = data.namaLayanan
                tvServiceEstimation.text = estimasi
                tvServicePrice.text = price
                tvServiceCount.text = data.qty.toString()

                btServiceAdd.setOnClickListener {
                    count++
                    tvServiceCount.text = count.toString()

                    data.qty = count
                    onItemClick?.invoke(data)
                }

                btServiceReduce.setOnClickListener {
                    if(count > 0) {
                        count--
                        tvServiceCount.text = count.toString()

                        data.qty = count
                        onItemClick?.invoke(data)
                    }
                }
            }
        }
    }
}