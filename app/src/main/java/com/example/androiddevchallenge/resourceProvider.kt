package com.example.androiddevchallenge

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey

class ResourceProvider(private val mContext: Context) {

    val timerDataStore = mContext.createDataStore(name = "AuthenticationData")

    object timerDataPreferenceKey{
        val timerKey = preferencesKey<Long>("timer")
        val modeKey = preferencesKey<Boolean>("mode")
    }
}