package com.example.kotlintodo.core.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kotlintodo.features.domain.entities.ToDoEntity

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todos ORDER BY dueDate ASC")
    fun getAllTodos(): LiveData<List<ToDoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): ToDoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: ToDoEntity)

    @Update
    suspend fun updateTodo(todo: ToDoEntity)

    @Delete
    suspend fun deleteTodo(todo: ToDoEntity)
}