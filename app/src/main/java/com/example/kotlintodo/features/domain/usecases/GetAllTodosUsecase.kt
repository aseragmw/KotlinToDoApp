package com.example.kotlintodo.features.domain.usecases

import com.example.kotlintodo.features.domain.repos.ToDoRepo
import javax.inject.Inject

class GetAllTodosUsecase @Inject constructor(
    private val todoRepo: ToDoRepo
){
    suspend operator fun invoke() = todoRepo.getAllTodos()
}