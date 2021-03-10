package com.example.androiddevchallenge

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
) : BaseViewModel() {

    val isDialogOn: MutableState<Boolean> = mutableStateOf(false)

    val time: MutableState<Long> = mutableStateOf(1L)


    fun getTime() = launch{
       time.value = dataStoreRepo.getTimer()
    }

    fun setTime(time:Long) = launch{
        dataStoreRepo.storeTime(time)
    }

}

