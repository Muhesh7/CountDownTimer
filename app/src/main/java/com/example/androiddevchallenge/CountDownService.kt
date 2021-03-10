package com.example.androiddevchallenge

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_LOW
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.androiddevchallenge.Constants.ACTION_START_SERVICE
import com.example.androiddevchallenge.Constants.ACTION_STOP_SERVICE
import com.example.androiddevchallenge.Constants.NOTIFICATION_CHANNEL_ID
import com.example.androiddevchallenge.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.androiddevchallenge.Constants.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CountDownService : LifecycleService() {
    companion object {
        val timerEvent = MutableLiveData<TimeEvent>(TimeEvent.DEFAULT)
        val timerInMillis = MutableLiveData<Long>(0L)
        val totalTimerInMillis = MutableLiveData<Long>(1L)
    }

    private var isServiceStopped = false
    private  var curEvent = TimeEvent.DEFAULT

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var dataStoreRepo: DataStoreRepo

    private var timeStarted = 0L
    private var lapTime = 0L
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_SERVICE -> {
                    startForegroundService()
                }
                ACTION_STOP_SERVICE -> {
                    stopService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun resetValues() {
        timerInMillis.value = 0L
        totalTimerInMillis.value = 1L
        timerEvent.value = TimeEvent.DEFAULT
    }

    private fun startForegroundService() {
        timerEvent.value = TimeEvent.START

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
        timerInMillis.observe(this, Observer {
            if (!isServiceStopped) {
                notificationBuilder.setContentText(
                    getFormattedTime(it, false)
                )
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            }
        })
        timerEvent.observe(this,{
            curEvent = it
            if(it==TimeEvent.START)
            {
                startTimer()
            }
        })

    }

    private fun stopService() {
        isServiceStopped = true
        Toast.makeText(this, "CountDown Stopped", Toast.LENGTH_SHORT).show()
        resetValues()
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(
            NOTIFICATION_ID
        )
        stopForeground(true)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_LOW
            )

        notificationManager.createNotificationChannel(channel)
    }

    private fun startTimer() {
        CoroutineScope(Dispatchers.Main).launch {
            while (curEvent == TimeEvent.START && !isServiceStopped) {
                timerInMillis.postValue(timerInMillis.value!! - 1000L)
                delay(1000L)
                if (timerInMillis.value!! <= 1000) {
                    stopService()
                    break
                }
            }
        }
    }
}
