package com.example.kotlintodo.core.di

import android.content.Context
import com.example.kotlintodo.core.db.TodoDAO
import com.example.kotlintodo.core.db.TodoDB
import com.example.kotlintodo.core.db.TodoDbBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Provides
    @Singleton
    fun getDB(@ApplicationContext context: Context): TodoDB {
        return TodoDbBuilder(context).getInstance()
    }

    @Provides
    @Singleton
    fun getDao(@ApplicationContext context: Context): TodoDAO {
        return getDB(context).todoDao()
    }

}