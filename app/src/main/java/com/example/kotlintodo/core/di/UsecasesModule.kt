package com.example.kotlintodo.core.di

import com.example.kotlintodo.features.domain.repos.ToDoRepo
import com.example.kotlintodo.features.domain.usecases.DeleteTodoUsecase
import com.example.kotlintodo.features.domain.usecases.GetAllTodosUsecase
import com.example.kotlintodo.features.domain.usecases.GetTodoByIdUsecase
import com.example.kotlintodo.features.domain.usecases.InsertTodoUsecase
import com.example.kotlintodo.features.domain.usecases.UpdateTodoUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsecasesModule {
    @Provides
    @Singleton
    fun provideGetAllTodosUsecase(todoRepo: ToDoRepo): GetAllTodosUsecase {
        return GetAllTodosUsecase(todoRepo)
    }

    @Provides
    @Singleton
    fun provideInsertTodoUsecase(todoRepo: ToDoRepo): InsertTodoUsecase {
        return InsertTodoUsecase(todoRepo)
    }

    @Provides
    @Singleton
    fun provideDeleteTodoUsecase(todoRepo: ToDoRepo): DeleteTodoUsecase {
        return DeleteTodoUsecase(todoRepo)
    }

    @Provides
    @Singleton
    fun provideUpdateTodoUsecase(todoRepo: ToDoRepo): UpdateTodoUsecase {
        return UpdateTodoUsecase(todoRepo)
    }

    @Provides
    @Singleton
    fun provideGetTodoByIdUsecase(todoRepo: ToDoRepo): GetTodoByIdUsecase {
        return GetTodoByIdUsecase(todoRepo)
    }


}