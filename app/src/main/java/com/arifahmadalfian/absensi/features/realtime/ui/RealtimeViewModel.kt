package com.arifahmadalfian.absensi.features.realtime.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifahmadalfian.absensi.features.realtime.model.RealtimeDto
import com.arifahmadalfian.absensi.features.realtime.repository.IRealtimeRepository
import com.arifahmadalfian.absensi.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val repo: IRealtimeRepository
) : ViewModel() {

    private val _res: MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res

    fun insert(items: RealtimeDto.RealtimeItems) = repo.insert(items)

    private val _updateRes: MutableState<RealtimeDto> = mutableStateOf(
        RealtimeDto(
            item = RealtimeDto.RealtimeItems(),
        )
    )
    val updateRes: State<RealtimeDto> = _updateRes

    fun setData(data: RealtimeDto) {
        _updateRes.value = data
    }

    init {

        viewModelScope.launch {
            repo.getItems().collect {
                when (it) {
                    is ResultState.Success -> {
                        _res.value = ItemState(
                            item = it.data
                        )
                    }
                    is ResultState.Failure -> {
                        _res.value = ItemState(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading -> {
                        _res.value = ItemState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun delete(key: String) = repo.delete(key)
    fun update(item: RealtimeDto) = repo.update(item)


}

data class ItemState(
    val item: List<RealtimeDto> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
