package com.example.ta_pmob

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_pmob.databinding.ActivityHomeBinding

class AdapterMaps(private val data: List<MapsImageModel>) :
    RecyclerView.Adapter<AdapterMaps.MapsViewHolder>() {

    class MapsViewHolder(private val binding: ActivityHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: MapsImageModel) {
//            binding.tvNama.text = content.nama
//            binding.tvShift.text = content.shift
//            binding.tvDurasi.text = content.durasi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsViewHolder {
        val binding = ActivityHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MapsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)

    }

    override fun getItemCount(): Int = data.size
}