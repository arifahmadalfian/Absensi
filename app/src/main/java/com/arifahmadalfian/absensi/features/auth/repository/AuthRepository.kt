package com.arifahmadalfian.absensi.features.auth.repository

import android.app.Activity
import android.util.Log
import com.arifahmadalfian.absensi.features.auth.model.AuthDto
import com.arifahmadalfian.absensi.utils.ResultState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authdb: FirebaseAuth
) : IAuthRepository {

    private lateinit var omVerificationCode: String

    override fun createUser(auth: AuthDto): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        authdb.createUserWithEmailAndPassword(
            auth.email!!,
            auth.password!!
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResultState.Success("User created successfully"))
                Log.d("main", "current user id: ${authdb.currentUser?.uid}")
            }
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    override fun loginUser(auth: AuthDto): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        authdb.signInWithEmailAndPassword(
            auth.email!!,
            auth.password!!
        ).addOnSuccessListener {
            trySend(ResultState.Success("login Successfully"))
            Log.d("main", "current user id: ${authdb.currentUser?.uid}")
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }

    override fun createUserWithPhone(phone: String, activity: Activity): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val onVerificationCallback =
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        trySend(ResultState.Failure(p0))
                    }

                    override fun onCodeSent(
                        verificationCode: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationCode, p1)
                        trySend(ResultState.Success("OTP Sent Successfully"))
                        omVerificationCode = verificationCode
                    }

                }

            val options = PhoneAuthOptions.newBuilder(authdb)
                .setPhoneNumber("+62$phone")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(onVerificationCallback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            awaitClose {
                close()
            }
        }

    override fun signWithCredential(otp: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val credential = PhoneAuthProvider.getCredential(omVerificationCode, otp)
        authdb.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("otp verified"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }
}