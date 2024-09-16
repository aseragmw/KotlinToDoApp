package com.example.kotlintodo.core.db

import android.content.Context
import androidx.room.Room
import javax.inject.Inject

class TodoDbBuilder @Inject constructor(
    private val context: Context
) {
    private var db: TodoDB? = null

    fun getInstance(): TodoDB {
        synchronized(this) {
            if (db == null) {
                db = Room.databaseBuilder(
                    context,
                    TodoDB::class.java,
                    TodoDB.DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return db!!
        }
        }
    }