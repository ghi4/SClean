package com.project.laundryapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.databinding.ItemLaundryHorizontalBinding
import com.project.laundryapp.databinding.ItemLaundryVerticalBinding

class LaundrySideAdapter : RecyclerView.Adapter<LaundrySideAdapter.LaundrySideViewHolder>() {

    private var dataList = ArrayList<Laundry>()
    var onItemClick: ((Laundry) -> Unit)? = null

    fun setList(data: ArrayList<Laundry>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundrySideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laundry_vertical, parent, false)
        return LaundrySideViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundrySideViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class LaundrySideViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryVerticalBinding.bind(itemView)
        fun bind(data: Laundry) {
            with(binding) {
                val openingHours = "Buka: ${data.jamBuka} - ${data.jamTutup}"
                tvLaundryVTitle.text = data.namaLaundry
                tvLaundryVAddress.text = data.alamat
                tvLaundryVOpeningHours.text = openingHours
                tvLaundryVOrderCount.text = 100.toString()
                tvLaundryVRating.text = 4.5.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }
    }
}