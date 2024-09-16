package com.example.kotlintodo

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlintodo.features.presentation.TodoViewModel
import com.example.kotlintodo.features.presentation.screens.AddToDoScreen
import com.example.kotlintodo.features.presentation.screens.home.HomeScreen
import com.example.kotlintodo.features.presentation.screens.ToDoDetailsScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
        val viewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        viewModel.getAllTodos()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(viewModel, navController) }
                composable("details/{item}") { backStackEntry ->
                    ToDoDetailsScreen(
                        navController,
                        backStackEntry,
                        viewModel,
                    )
                }
                composable("add") {
                    AddToDoScreen(viewModel, navController)
                }
            }
        }
    }
}

