package com.project.laundryapp.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.laundryapp.BuildConfig
import com.project.laundryapp.R
import com.project.laundryapp.core.data.remote.response.promotion.PromotionResponse
import com.project.laundryapp.databinding.ItemLaundryBannerBinding
import com.project.laundryapp.utils.Const
import com.project.laundryapp.utils.Utils
import com.squareup.picasso.Picasso

class BannerAdapter(val context: Context) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private var bannerList = ArrayList<PromotionResponse>()
    var onItemClick: ((PromotionResponse) -> Unit)? = null

    fun setList(data: ArrayList<PromotionResponse>) {
        bannerList.clear()
        bannerList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_laundry_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLaundryBannerBinding.bind(itemView)
        fun bind(promotion: PromotionResponse) {
            with(binding) {

                val circular = Utils.getCircularProgressDrawable(context)
                //Banner Image
                Picasso.get()
                    .load(BuildConfig.BASE_URL + promotion.photoURL)
                    .placeholder(circular)
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