package com.arifahmadalfian.absensi.features.firestore.model

data class FirestoreDto(
    val item:FirestoreItem?,
    val key:String? = ""
){

    data class FirestoreItem(
        val title:String? = "",
        val description:String? = ""
    )

}