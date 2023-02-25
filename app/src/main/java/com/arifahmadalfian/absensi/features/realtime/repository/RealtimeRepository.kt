package com.arifahmadalfian.absensi.features.realtime.repository

import com.arifahmadalfian.absensi.features.realtime.model.RealtimeDto
import com.arifahmadalfian.absensi.utils.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeRepository @Inject constructor(
    private val db: DatabaseReference
) : IRealtimeRepository {

    override fun insert(item: RealtimeDto.RealtimeItems): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            db.push().setValue(
                item
            ).addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("Data inserted Successfully.."))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

            awaitClose {
                close()
            }
        }

    override fun getItems(): Flow<ResultState<List<RealtimeDto>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    RealtimeDto(
                        it.getValue(RealtimeDto.RealtimeItems::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }

        db.addValueEventListener(valueEvent)
        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }

    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.child(key).removeValue()
            .addOnCompleteListener {
                trySend(ResultState.Success("item Deleted"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun update(res: RealtimeDto): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val map = HashMap<String, Any>()
        map["title"] = res.item?.title!!
        map["description"] = res.item.description!!

        db.child(res.key!!).updateChildren(
            map
        ).addOnCompleteListener {
            trySend(ResultState.Success("Update successfully..."))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }
}