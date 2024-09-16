package com.example.kotlintodo.features.domain.repos

import androidx.lifecycle.LiveData
import com.example.kotlintodo.features.domain.entities.ToDoEntity

interface ToDoRepo {
    suspend fun getAllTodos(): LiveData<List<ToDoEntity>>
    suspend fun getTodoById(id:Int):ToDoEntity?
    suspend fun insertTodo(todo: ToDoEntity)
    suspend fun deleteTodo(todo:ToDoEntity)
    suspend fun updateTodo(todo:ToDoEntity)
}