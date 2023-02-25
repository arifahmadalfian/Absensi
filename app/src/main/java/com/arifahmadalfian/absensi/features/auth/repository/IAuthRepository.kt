package com.arifahmadalfian.absensi.features.auth.repository

import android.app.Activity
import com.arifahmadalfian.absensi.features.auth.model.AuthDto
import com.arifahmadalfian.absensi.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    fun createUser(
        auth: AuthDto
    ): Flow<ResultState<String>>

    fun loginUser(
        auth: AuthDto
    ): Flow<ResultState<String>>

    fun createUserWithPhone(
        phone: String,
        activity: Activity
    ): Flow<ResultState<String>>

    fun signWithCredential(
        otp: String
    ): Flow<ResultState<String>>

}