package com.example.kotlintodo.features.data.repos

import androidx.lifecycle.LiveData
import com.example.kotlintodo.features.data.datasources.local.TodoLocalDataSource
import com.example.kotlintodo.features.domain.entities.ToDoEntity
import com.example.kotlintodo.features.domain.repos.ToDoRepo
import javax.inject.Inject

class TodoRepoImpl @Inject constructor(
    private val todoLocalDataSource: TodoLocalDataSource

): ToDoRepo {
    override suspend fun getAllTodos(): LiveData<List<ToDoEntity>> = todoLocalDataSource.getAllTodos()


    override suspend fun getTodoById(id: Int): ToDoEntity? {
        return todoLocalDataSource.getTodoById(id)
    }

    override suspend fun insertTodo(todo: ToDoEntity) {
        todoLocalDataSource.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: ToDoEntity) {
        todoLocalDataSource.deleteTodo(todo)
    }

    override suspend fun updateTodo(todo: ToDoEntity) {
        todoLocalDataSource.updateTodo(todo)
    }
}