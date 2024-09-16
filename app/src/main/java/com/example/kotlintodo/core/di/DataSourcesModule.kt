package com.example.kotlintodo.core.di

import android.content.Context
import com.example.kotlintodo.core.db.TodoDAO
import com.example.kotlintodo.features.data.datasources.local.TodoLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourcesModule {
    @Provides
    @Singleton
    fun provideTodoLocalDataSource(todoDao: TodoDAO, @ApplicationContext context: Context): TodoLocalDataSource {
        return TodoLocalDataSource(todoDao,context)
    }

}