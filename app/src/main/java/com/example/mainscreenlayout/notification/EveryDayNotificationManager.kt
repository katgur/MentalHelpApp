package com.example.mainscreenlayout.notification

import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.mainscreenlayout.R
import java.util.*

class EveryDayNotificationManager {

    private val CHANNEL_ID = "reminder"
    private val CHANNEL_NAME = "reminder"
    private val REQUEST_CODE = 44
    private val HOUR = 20
    private val MINUTE = 0

    private val TAG = "EveryDayNotificationManager"

    @RequiresApi(Build.VERSION_CODES.O)
    fun startNotifications(context : Context) {
        setAlarm(context)
        createNotificationsChannel(context)
        Log.d(TAG, "started notifications")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stopNotifications(context : Context) {
        unsetAlarm(context)
        deleteNotificationChannel(context)
        Log.d(TAG, "stopped notifications")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendRemindingNotification(context : Context) {
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Привет!")
            .setContentText("Вы не заходили в приложение сегодня.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(REQUEST_CODE, builder.build())
    }

    private fun setAlarm(context : Context) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, HOUR)
            set(Calendar.MINUTE, MINUTE)
        }

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 45, intent, 0)

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun unsetAlarm(context : Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 45, intent, 0)

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationsChannel(context : Context) {
        val mChannel = NotificationChannel(CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteNotificationChannel(context : Context) {
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
    }
}