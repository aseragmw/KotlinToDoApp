package com.example.kotlintodo.core.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlintodo.features.domain.entities.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1)
abstract class TodoDB :RoomDatabase() {
    companion object{
        val DB_NAME = "todo_db"
    }
    abstract fun todoDao(): TodoDAO
}