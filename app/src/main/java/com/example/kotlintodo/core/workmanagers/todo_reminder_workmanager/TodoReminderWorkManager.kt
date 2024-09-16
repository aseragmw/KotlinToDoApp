package com.example.kotlintodo.core.workmanagers.todo_reminder_workmanager

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.kotlintodo.R
import com.example.kotlintodo.core.services.NotificarionsService

class TodoReminderWorkManager(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {
    companion object {
        const val TODO_ID = "todoId"
        const val TODO_TITLE = "todoTitle"
        const val TODO_CONTENT = "todoContent"
        const val CHANNEL_ID = "TODO_REMINDER_CHANNEL"
    }

    override suspend fun doWork(): Result {
        val todoId: Int
        val todoTitle: String
        val todoContent: String
        try {
            todoId = params.inputData.getInt("todoId", -1)
            todoTitle = params.inputData.getString("todoTitle") ?: ""
            todoContent = params.inputData.getString("todoContent") ?: ""
        } catch (e: Exception) {
            return Result.failure()
        }
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(todoTitle)
            .setContentText(todoContent)
            .build()

        val service = NotificarionsService(context)
        service.showNotification(notification)
        return Result.success()
    }

}