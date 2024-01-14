package com.example.ta_pmob.models

import java.io.Serializable

data class MapsImageModel(
    val imageId: Int,
    val namaWisata: String,
    val namaKota: String,
    val photoUrl: String,

//    val description: String? = null,
//    val country: String? = null,
//    val rating: Float? = null,
//    val jarakSatuan: Int? = null

) : Serializable {
    constructor() : this(
        imageId = -1,
        photoUrl = "",
        namaWisata = "",
        namaKota = ""
    )
}
