package com.project.laundryapp.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.response.laundry.LaundryDataResponse
import com.project.laundryapp.databinding.ItemLaundryHorizontalBinding
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import com.squareup.picasso.Picasso

class LaundryTopAdapter(val context: Context) :
    RecyclerView.Adapter<LaundryTopAdapter.LaundryTopViewHolder>() {

    private var dataList = ArrayList<LaundryDataResponse>()
    var onItemClick: ((LaundryDataResponse) -> Unit)? = null

    fun setList(data: ArrayList<LaundryDataResponse>) {
        val recommendedLaundry = data.filter { it.isRecommend == 1 }
        dataList.clear()
        dataList.addAll(recommendedLaundry)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryTopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laundry_horizontal, parent, false)
        return LaundryTopViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundryTopViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class LaundryTopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryHorizontalBinding.bind(itemView)
        fun bind(data: LaundryDataResponse) {
            with(binding) {
                val open = Utils.parseHours(data.jamBuka.toString())
                val close = Utils.parseHours(data.jamTutup.toString())
                val openingHours = "$open - $close"
                val shipmentPrice = Utils.parseIntToCurrency(data.biayaPengantaran)
                tvLaundryTitle.text = data.namaLaundry
                tvLaundryAddress.text = data.alamat
                tvLaundryOpeningHours.text = openingHours
                tvLaundryShipmentPrice.text = shipmentPrice

                val circular = Utils.getCircularProgressDrawable(context)
                Picasso.get()
                    .load(Const.URL_BASE + data.photo)
                    .placeholder(circular)
                    .error(R.drawable.square_placeholder)
                    .resize(Const.SQUARE_TARGET_SIZE, Const.SQUARE_TARGET_SIZE)
                    .into(ivLaundryImage)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }
    }
}