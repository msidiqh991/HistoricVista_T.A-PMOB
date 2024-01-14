package com.example.ta_pmob.models

import java.io.Serializable

data class MapsImageModel(
    val imageId: Int,
    val namaWisata: String,
    val namaKota: String,
    val photoUrl: String,
    val description: String,
    val rating: Float

) : Serializable {
    constructor() : this(
        imageId = -1,
        photoUrl = "",
        namaWisata = "",
        namaKota = "",
        description = "",
        rating = 0.0f
    )
}
