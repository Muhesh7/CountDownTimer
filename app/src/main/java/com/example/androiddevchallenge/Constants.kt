package com.example.androiddevchallenge

import java.util.*

object Constants {

    const val NOTIFICATION_CHANNEL_ID = "1"
    const val NOTIFICATION_ID = 7
    const val  NOTIFICATION_CHANNEL_NAME = "notify"
    const val ACTION_START_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val NOTIFICATION_REQUEST_CODE = 1
    val Minutes: List<Int> = m()
//    arrayListOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30)
    val Hours: ArrayList<Int> = h()
    val Seconds: List<Int> = m()

    fun m(): ArrayList<Int> {
        val ms = arrayListOf<Int>()
        for(i in 0..59)
          ms.add(i)
        return ms
    }
    fun h(): ArrayList<Int> {
        val h = arrayListOf<Int>()
        for(i in 0..24)
            h.add(i)
        return h
    }
    private fun IntRange.toIntArray():IntArray{
        if(last>first)
         return IntArray(0)
        val result = IntArray(last- first +1)
        var index =0
        for(i in this)
         result[index++] = i
        return result
    }
}