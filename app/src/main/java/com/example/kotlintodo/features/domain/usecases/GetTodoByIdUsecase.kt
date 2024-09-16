package com.example.kotlintodo.features.domain.usecases

import com.example.kotlintodo.features.domain.repos.ToDoRepo
import javax.inject.Inject

class GetTodoByIdUsecase @Inject constructor(
    private val todoRepo: ToDoRepo
){
    suspend operator fun invoke(id:Int) = todoRepo.getTodoById(id)
}