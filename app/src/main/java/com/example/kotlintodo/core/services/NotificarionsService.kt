package com.example.kotlintodo.core.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import javax.inject.Inject

class NotificarionsService @Inject constructor(
    val context: Context
) {
    companion object {
        var notificationID = 0
        const val CHANNEL_ID = "TODO_REMINDER_CHANNEL"
    }

    var notificationManager: NotificationManager

    init {
        notificationManager =
            context.getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Todo Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(notification: Notification) {
        notificationManager.notify(++notificationID, notification)
    }
}