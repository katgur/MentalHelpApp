package com.example.mainscreenlayout.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class AlarmReceiver : BroadcastReceiver() {

    private val notificationFactory = EveryDayNotificationManager()
    private val TAG = "EveryDayNotificationManager"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            notificationFactory.sendRemindingNotification(context)
        }
        Log.d(TAG, "received alarm")
    }
}