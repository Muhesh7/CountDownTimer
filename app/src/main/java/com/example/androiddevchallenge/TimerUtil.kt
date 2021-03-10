package com.example.androiddevchallenge

import android.content.Intent
import dagger.hilt.android.qualifiers.ActivityContext
import java.util.concurrent.TimeUnit

fun getFormattedTime(timeInMillis: Long, includeMillis: Boolean): String {
    var milliseconds = timeInMillis
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
    milliseconds -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
    if (!includeMillis) {
        return "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds"
    }
    milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
    milliseconds /= 10
    return "${if (hours < 10) "0" else ""}$hours:" +
            "${if (minutes < 10) "0" else ""}$minutes:" +
            "${if (seconds < 10) "0" else ""}$seconds:" +
            "${if (milliseconds < 10) "0" else ""}$milliseconds"
}

fun getFormattedTimeUnit(time: Int): String {
    return "${if (time < 10) "0" else ""}$time"
}

fun circularList(index: Int, size: Int): Int {
    return when {
        index < 0 -> size - 1
        index > size - 1 -> 0
        else -> index
    }
}

fun toMillis(h:Int,m:Int,s:Int):Long{
    return ((3600000*h)+(60000*m)+(1000*s)).toLong()
}

fun sendCommandToService(action: String,@ActivityContext context: MainActivity) {
    context.startService(Intent(context, CountDownService::class.java).apply {
        this.action = action
    })
}

