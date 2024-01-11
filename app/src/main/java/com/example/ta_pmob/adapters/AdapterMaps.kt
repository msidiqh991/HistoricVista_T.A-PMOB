package com.example.ta_pmob.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ta_pmob.HomeDetailActivity
import com.example.ta_pmob.HomeDetailActivity.Companion.DATA_KOTA
import com.example.ta_pmob.HomeDetailActivity.Companion.DATA_WISATA
import com.example.ta_pmob.databinding.ItemLocationBinding
import com.example.ta_pmob.models.MapsImageModel

class AdapterMaps(private val mapsImageList: List<MapsImageModel>) :
    RecyclerView.Adapter<AdapterMaps.MapsViewHolder>() {

    private var listener: OnItemClickListener? = null
    private var imageIdListener: OnImageIdClickListener? = null

    class MapsViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mapsModel: MapsImageModel) {
            binding.tvNamaWisata.text = mapsModel.namaWisata
            binding.tvNamaKota.text = mapsModel.namaKota
            Glide.with(binding.root.context)
                .load(mapsModel.photoUrl)
                .centerCrop()
                .into(binding.ivPhotos)

            // TODO : MAINTENANCE TO ACCESS DETAIL_ACTIVTIY
            binding.root.setOnClickListener {
                val detailMapsIntent = Intent(it.context, HomeDetailActivity::class.java)
                detailMapsIntent.putExtra(DATA_WISATA, mapsModel.namaWisata)
                detailMapsIntent.putExtra(DATA_KOTA, mapsModel.namaKota)
                it.context.startActivity(detailMapsIntent)

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsViewHolder {
        val binding = ItemLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MapsViewHolder(binding)
    }

    override fun getItemCount(): Int = mapsImageList.size

    interface OnImageIdClickListener {
        fun onImageIdClick(imageId: Int)
    }

    fun setOnImageIdClickListener(listener: OnImageIdClickListener) {
        this.imageIdListener = listener
    }

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        val data = mapsImageList[position]
        holder.bind(data)

        holder.itemView.setOnClickListener {
            imageIdListener?.onImageIdClick(data.imageId ?: -1)
            listener?.onItemClick(holder.adapterPosition)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}