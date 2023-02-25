package com.arifahmadalfian.absensi.features.firestore.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifahmadalfian.absensi.features.firestore.model.FirestoreDto
import com.arifahmadalfian.absensi.features.firestore.repository.IFirestoreRepository
import com.arifahmadalfian.absensi.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val repo: IFirestoreRepository
) : ViewModel() {

    private val _res: MutableState<FirestoreState> = mutableStateOf(FirestoreState())
    val res: State<FirestoreState> = _res

    fun insert(item: FirestoreDto.FirestoreItem) = repo.insert(item)

    private val _updateData: MutableState<FirestoreDto> = mutableStateOf(
        FirestoreDto(
            item = FirestoreDto.FirestoreItem()
        )
    )
    val updateData: State<FirestoreDto> = _updateData

    fun setData(data: FirestoreDto) {
        _updateData.value = data
    }

    init {
        getItems()
    }

    fun getItems() = viewModelScope.launch {
        repo.getItems().collect {
            when (it) {
                is ResultState.Success -> {
                    _res.value = FirestoreState(
                        data = it.data
                    )
                }
                is ResultState.Failure -> {
                    _res.value = FirestoreState(
                        error = it.msg.toString()
                    )
                }
                ResultState.Loading -> {
                    _res.value = FirestoreState(
                        isLoading = true
                    )
                }
            }
        }
    }

    fun delete(key: String) = repo.delete(key)
    fun update(item: FirestoreDto) = repo.update(item)

}

data class FirestoreState(
    val data: List<FirestoreDto> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)