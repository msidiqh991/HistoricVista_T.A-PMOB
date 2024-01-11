package com.example.ta_pmob.models

import java.io.Serializable

data class MapsImageModel(
    val imageId: Int? = null,
    val namaWisata: String? = null,
    val namaKota: String? = null,
    val photoUrl: String? = null,

//    val description: String? = null,
//    val country: String? = null,
//    val rating: Float? = null,
//    val jarakSatuan: Int? = null

) : Serializable
