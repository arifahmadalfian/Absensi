package com.arifahmadalfian.absensi.features.auth.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.arifahmadalfian.absensi.features.auth.model.AuthDto
import com.arifahmadalfian.absensi.features.auth.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: IAuthRepository
) : ViewModel() {

    fun createUser(authUser: AuthDto) = repo.createUser(authUser)

    fun loginUser(authUser: AuthDto) = repo.loginUser(authUser)

    fun createUserWithPhone(
        mobile: String,
        activity: Activity
    ) = repo.createUserWithPhone(mobile, activity)

    fun signInWithCredential(
        code: String
    ) = repo.signWithCredential(code)

}