package com.example.kotlintodo.features.domain.usecases

import com.example.kotlintodo.features.domain.entities.ToDoEntity
import com.example.kotlintodo.features.domain.repos.ToDoRepo
import javax.inject.Inject

class UpdateTodoUsecase @Inject constructor(
    private val todoRepo: ToDoRepo
){
    suspend operator fun invoke(todo:ToDoEntity) = todoRepo.updateTodo(todo)
}