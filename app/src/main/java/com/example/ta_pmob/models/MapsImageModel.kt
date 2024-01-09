package com.example.ta_pmob.models

import java.io.Serializable

data class MapsImageModel(
    val imageId: Int? = null,
    val namaWisata: String? = null,
    val namaKota: String? = null,
    val photoUrl: String? = null,
) : Serializable
