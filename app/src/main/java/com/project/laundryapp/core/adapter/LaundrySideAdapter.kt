package com.project.laundryapp.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.response.laundry.LaundryDataResponse
import com.project.laundryapp.databinding.ItemLaundryVerticalBinding
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import com.squareup.picasso.Picasso

class LaundrySideAdapter(val context: Context) :
    RecyclerView.Adapter<LaundrySideAdapter.LaundrySideViewHolder>() {

    private var dataList = ArrayList<LaundryDataResponse>()
    var onItemClick: ((LaundryDataResponse) -> Unit)? = null

    fun setList(data: ArrayList<LaundryDataResponse>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundrySideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laundry_vertical, parent, false)
        return LaundrySideViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundrySideViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class LaundrySideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryVerticalBinding.bind(itemView)
        fun bind(data: LaundryDataResponse) {
            with(binding) {
                val open = Utils.parseHours(data.jamBuka.toString())
                val close = Utils.parseHours(data.jamTutup.toString())
                val openingHours = "$open - $close"
                val shipmentPrice = Utils.parseIntToCurrency(data.biayaPengantaran)
                tvLaundryVTitle.text = data.namaLaundry
                tvLaundryVAddress.text = data.alamat
                tvLaundryVOpeningHours.text = openingHours
                tvLaundryVShipmentPrice.text = shipmentPrice

                val circular = Utils.getCircularProgressDrawable(context)
                Picasso.get()
                    .load(Const.URL_BASE + data.photo)
                    .placeholder(circular)
                    .error(R.drawable.square_placeholder)
                    .resize(Const.SQUARE_TARGET_SIZE, Const.SQUARE_TARGET_SIZE)
                    .into(ivLaundryVImage)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }
    }
}