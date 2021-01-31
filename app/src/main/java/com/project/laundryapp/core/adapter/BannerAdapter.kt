package com.project.laundryapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.R
import com.project.laundryapp.core.data.local.Laundry
import com.project.laundryapp.databinding.ItemLaundryBannerBinding
import com.project.laundryapp.utils.Const
import com.squareup.picasso.Picasso

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private var bannerList = ArrayList<Laundry>()
    var onItemClick: ((Laundry) -> Unit)? = null

    fun setList(data: ArrayList<Laundry>){
        bannerList.clear()
        bannerList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laundry_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size

    inner class BannerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryBannerBinding.bind(itemView)
        fun bind(laundry: Laundry){
            with(binding) {

                //Banner Image
                Picasso.get()
                    .load(laundry.foto)
                    .placeholder(R.drawable.banner_placeholder)
                    .error(R.drawable.banner_placeholder)
                    .resize(Const.BANNER_TARGET_WIDTH, Const.BANNER_TARGET_HEIGHT)
                    .into(ivBanner)
            }

        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(bannerList[adapterPosition])
            }
        }
    }
}