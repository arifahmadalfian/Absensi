package com.arifahmadalfian.absensi.features.firestore.repository

import com.arifahmadalfian.absensi.features.firestore.model.FirestoreDto
import com.arifahmadalfian.absensi.utils.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val db:FirebaseFirestore
) : IFirestoreRepository{

    override fun insert(item: FirestoreDto.FirestoreItem): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        db.collection("user")
            .add(item)
            .addOnSuccessListener {
                trySend(ResultState.Success("Data is inserted with ${it.id}"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun getItems(): Flow<ResultState<List<FirestoreDto>>> =  callbackFlow{
        trySend(ResultState.Loading)
        db.collection("user")
            .get()
            .addOnSuccessListener {
                val items =  it.map { data->
                    FirestoreDto(
                        item = FirestoreDto.FirestoreItem(
                            title = data["title"] as String?,
                            description = data["description"] as String?
                        ),
                        key = data.id
                    )
                }
                trySend(ResultState.Success(items))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose {
            close()
        }
    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        db.collection("user")
            .document(key)
            .delete()
            .addOnCompleteListener {
                if(it.isSuccessful)
                    trySend(ResultState.Success("Deleted successfully.."))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun update(item: FirestoreDto): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        val map = HashMap<String,Any>()
        map["title"] = item.item?.title!!
        map["description"] = item.item.description!!

        db.collection("user")
            .document(item.key!!)
            .update(map)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    trySend(ResultState.Success("Update successfully..."))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }
}