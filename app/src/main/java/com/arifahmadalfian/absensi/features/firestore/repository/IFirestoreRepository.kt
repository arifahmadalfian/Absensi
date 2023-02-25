package com.arifahmadalfian.absensi.features.firestore.repository

import com.arifahmadalfian.absensi.features.firestore.model.FirestoreDto
import com.arifahmadalfian.absensi.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface IFirestoreRepository {

    fun insert(
        item: FirestoreDto.FirestoreItem
    ): Flow<ResultState<String>>

    fun getItems(): Flow<ResultState<List<FirestoreDto>>>

    fun delete(key: String): Flow<ResultState<String>>

    fun update(
        item: FirestoreDto
    ): Flow<ResultState<String>>

}