package com.project.laundryapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.databinding.ItemLaundryHorizontalBinding

class LaundryTopAdapter : RecyclerView.Adapter<LaundryTopAdapter.LaundryTopViewHolder>() {

    private var dataList = ArrayList<Laundry>()
    var onItemClick: ((Laundry) -> Unit)? = null

    fun setList(data: ArrayList<Laundry>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryTopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laundry_horizontal, parent, false)
        return LaundryTopViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundryTopViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class LaundryTopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryHorizontalBinding.bind(itemView)
        fun bind(data: Laundry) {
            with(binding) {
                val openingHours = "Buka: ${data.jamBuka} - ${data.jamTutup}"
                tvLaundryTitle.text = data.namaLaundry
                tvLaundryAddress.text = data.alamat
                tvLaundryOpeningHours.text = openingHours
                tvLaundryOrderCount.text = 100.toString()
                tvLaundryRating.text = 4.5.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }
    }
}