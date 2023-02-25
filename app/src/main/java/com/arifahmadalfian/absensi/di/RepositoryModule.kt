package com.arifahmadalfian.absensi.di

import com.arifahmadalfian.absensi.features.auth.repository.AuthRepository
import com.arifahmadalfian.absensi.features.auth.repository.IAuthRepository
import com.arifahmadalfian.absensi.features.firestore.repository.FirestoreRepository
import com.arifahmadalfian.absensi.features.firestore.repository.IFirestoreRepository
import com.arifahmadalfian.absensi.features.realtime.repository.IRealtimeRepository
import com.arifahmadalfian.absensi.features.realtime.repository.RealtimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRealtimeRepository(
        repo: RealtimeRepository
    ): IRealtimeRepository

    @Binds
    abstract fun providesFirestoreRepository(
        repo: FirestoreRepository
    ): IFirestoreRepository

    @Binds
    abstract fun providesFirebaseAuthRepository(
        repo: AuthRepository
    ): IAuthRepository

}