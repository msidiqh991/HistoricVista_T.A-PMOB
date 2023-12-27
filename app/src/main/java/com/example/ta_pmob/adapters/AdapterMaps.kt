package com.example.ta_pmob.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ta_pmob.databinding.ItemLocationBinding
import com.example.ta_pmob.models.MapsImageModel

class AdapterMaps(private val mapsImageList: List<MapsImageModel>) :
    RecyclerView.Adapter<AdapterMaps.MapsViewHolder>() {

    private var listener: OnItemClickListener? = null

    class MapsViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mapsModel: MapsImageModel) {
            binding.tvNamaWisata.text = mapsModel.namaWisata
            binding.tvNamaKota.text = mapsModel.namaKota
            Glide.with(binding.root.context)
                .load(mapsModel.photoUrl)
                .centerCrop()
                .into(binding.ivPhotos)
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

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        val data = mapsImageList[position]
        holder.bind(data)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}