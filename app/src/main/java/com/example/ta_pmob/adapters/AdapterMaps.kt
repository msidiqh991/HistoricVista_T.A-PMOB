package com.example.ta_pmob.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ta_pmob.databinding.ItemLocationBinding
import com.example.ta_pmob.models.MapsImageModel

class AdapterMaps(private val data: List<MapsImageModel>) :
    RecyclerView.Adapter<AdapterMaps.MapsViewHolder>() {

    class MapsViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: MapsImageModel) {
            binding.tvNamaWisata.text = content.namaWisata
            binding.tvNamaKota.text = content.namaKota
            Glide.with(binding.root.context)
                .load(content.photoUrl)
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

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)
    }
}