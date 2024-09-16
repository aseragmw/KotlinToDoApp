package com.example.kotlintodo.features.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintodo.features.domain.entities.ToDoEntity
import com.example.kotlintodo.features.domain.usecases.DeleteTodoUsecase
import com.example.kotlintodo.features.domain.usecases.GetAllTodosUsecase
import com.example.kotlintodo.features.domain.usecases.GetTodoByIdUsecase
import com.example.kotlintodo.features.domain.usecases.InsertTodoUsecase
import com.example.kotlintodo.features.domain.usecases.UpdateTodoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getAllTodosUsecase: GetAllTodosUsecase,
    private val insertTodoUsecase: InsertTodoUsecase,
    private val deleteTodoUsecase: DeleteTodoUsecase,
    private val updateTodoUsecase: UpdateTodoUsecase,
    private val getTodoByIdUsecase: GetTodoByIdUsecase
) : ViewModel() {
    var todos: LiveData<List<ToDoEntity>> = MutableLiveData()
    var selectedTodo = mutableStateOf<ToDoEntity?>(null)

    fun getAllTodos() {
        viewModelScope.launch {
            todos = getAllTodosUsecase.invoke()
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch {
            selectedTodo.value = getTodoByIdUsecase.invoke(id)
        }

    }

    fun insertTodo(todo: ToDoEntity) {
        viewModelScope.launch {
            insertTodoUsecase.invoke(todo)
        }

    }

    fun deleteTodo(todo: ToDoEntity) {
        viewModelScope.launch {
            deleteTodoUsecase.invoke(todo)
        }

    }

    fun updateTodo(todo: ToDoEntity) {
        viewModelScope.launch {
            updateTodoUsecase.invoke(todo)
        }
    }

}