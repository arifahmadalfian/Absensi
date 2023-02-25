package com.arifahmadalfian.absensi.features.realtime.repository

import com.arifahmadalfian.absensi.features.realtime.model.RealtimeDto
import com.arifahmadalfian.absensi.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface IRealtimeRepository {

    fun insert(
        item: RealtimeDto.RealtimeItems
    ) : Flow<ResultState<String>>

    fun getItems() : Flow<ResultState<List<RealtimeDto>>>

    fun delete(
        key:String
    ) : Flow<ResultState<String>>

    fun update(
        res: RealtimeDto
    ) : Flow<ResultState<String>>

}