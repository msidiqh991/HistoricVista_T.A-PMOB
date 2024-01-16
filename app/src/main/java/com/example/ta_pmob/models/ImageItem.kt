package com.example.ta_pmob.models

import java.io.Serializable

data class ImageItem(
    val imageId: Int,
    val photoUrl: String,
    val namaWisata: String,
    val namaKota: String,

) : Serializable