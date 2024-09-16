package com.example.kotlintodo.features.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var title:String,
    var content:String,
    var dueDate:Long
)