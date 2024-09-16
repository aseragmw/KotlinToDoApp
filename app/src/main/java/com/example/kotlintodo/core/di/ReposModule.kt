package com.example.kotlintodo.core.di

import com.example.kotlintodo.features.data.datasources.local.TodoLocalDataSource
import com.example.kotlintodo.features.data.repos.TodoRepoImpl
import com.example.kotlintodo.features.domain.repos.ToDoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReposModule {
    @Provides
    @Singleton
    fun provideTodoRepo(todoLocalDataSource: TodoLocalDataSource): ToDoRepo {
        return TodoRepoImpl(todoLocalDataSource)
    }

}