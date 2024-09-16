package com.example.kotlintodo.features.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kotlintodo.features.presentation.TodoViewModel
import com.example.kotlintodo.features.presentation.composables.ToDoItem

@Composable
fun HomeScreen(viewModel: TodoViewModel, navController: NavHostController) {
    val todos by viewModel.todos.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add")
                },
                containerColor = Color(0xFF24A19C),
                contentColor = Color.White,
                shape = FloatingActionButtonDefaults.shape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0E5ED))
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        text = " Reminders",
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(todos.size) {
                        ToDoItem(todo = todos[it], viewModel, navController)
                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }

            }
        }
    )
}