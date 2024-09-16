package com.example.kotlintodo.features.data.datasources.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.kotlintodo.core.db.TodoDAO
import com.example.kotlintodo.core.workmanagers.todo_reminder_workmanager.TodoReminderWorkManager
import com.example.kotlintodo.features.domain.entities.ToDoEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class TodoLocalDataSource @Inject constructor(
    private val todoDao: TodoDAO,
    private val context: Context
) {
    suspend fun getAllTodos(): LiveData<List<ToDoEntity>> {
        return todoDao.getAllTodos()
    }

    suspend fun getTodoById(id: Int): ToDoEntity? {
        return todoDao.getTodoById(id)
    }

    suspend fun insertTodo(todo: ToDoEntity) {
        todoDao.insertTodo(todo)
        val notificationWork = OneTimeWorkRequestBuilder<TodoReminderWorkManager>()
            //Todo replace 5 Seconds with todo.dueDate-System.currentTimeMillis()
            .setInitialDelay(20000,TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    TodoReminderWorkManager.TODO_ID to todo.id,
                    TodoReminderWorkManager.TODO_TITLE to todo.title,
                    TodoReminderWorkManager.TODO_CONTENT to todo.content
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(notificationWork)
    }

    suspend fun deleteTodo(todo: ToDoEntity) {
        todoDao.deleteTodo(todo)
    }

    suspend fun updateTodo(todo: ToDoEntity) {
        todoDao.updateTodo(todo)
    }

}