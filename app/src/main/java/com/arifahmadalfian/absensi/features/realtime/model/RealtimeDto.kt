package com.arifahmadalfian.absensi.features.realtime.model

data class RealtimeDto(
    val item: RealtimeItems?,
    val key:String? = ""
){

    data class RealtimeItems(
        val title:String? = "",
        val description:String? = ""
    )

}