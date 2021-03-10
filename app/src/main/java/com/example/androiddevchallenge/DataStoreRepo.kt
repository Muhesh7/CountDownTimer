package com.example.androiddevchallenge

import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepo @Inject constructor(private val resourceProvider:ResourceProvider) {

    val roleKey = preferencesKey<String>("timer")
    val modeKey = preferencesKey<Boolean>("mode")

    val timer: Flow<Long> = resourceProvider.timerDataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
        } else {
            throw it
        }
    }.map { preference ->
        preference[ResourceProvider.timerDataPreferenceKey.timerKey] ?: 1L
    }

    val isDarkMode: Flow<Boolean> = resourceProvider.timerDataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
        } else {
            throw it
        }
    }.map { preference ->
        preference[ResourceProvider.timerDataPreferenceKey.modeKey] ?: false
    }

    suspend fun getTimer(): Long {
        return timer.first()
    }


    suspend fun storeTime(time: Long) {
        resourceProvider.timerDataStore.edit {
            it[ResourceProvider.timerDataPreferenceKey.timerKey] = time
        }
    }

    suspend fun storeMode(isDarkMode: Boolean) {
        resourceProvider.timerDataStore.edit {
            it[ResourceProvider.timerDataPreferenceKey.modeKey] = isDarkMode
        }
    }

}