package com.example.kotlintodo.features.presentation.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.kotlintodo.R
import com.example.kotlintodo.features.domain.entities.ToDoEntity
import com.example.kotlintodo.features.presentation.TodoViewModel
import com.example.kotlintodo.features.presentation.composables.ToDoItem
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ToDoDetailsScreen(navController: NavHostController,navBackStackEntry: NavBackStackEntry, viewModel: TodoViewModel) {
    val context= LocalContext.current
    val jsonObj = navBackStackEntry.arguments?.getString("item")
    val todo = Gson().fromJson(jsonObj, ToDoEntity::class.java)
    var titleValue by remember {
        mutableStateOf(todo.title)
    }
    var contentValue by remember {
        mutableStateOf(todo.content)
    }
    var selectedDateTimeInMillis by remember { mutableStateOf(todo.dueDate) }

    var formattedTime by remember {
        mutableStateOf(SimpleDateFormat("HH:mm", Locale.getDefault()).format(
            Date(selectedDateTimeInMillis)))
    }
    var formattedDate by remember {
        mutableStateOf(SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(
            Date(selectedDateTimeInMillis)
        ))
    }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var hour = calendar.get(Calendar.HOUR)
    var minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)
    val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        // This is the onTimeSet listener where you handle the selected time
        hour = selectedHour // Update the selected hour
        minute = selectedMinute // Update the selected minute
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        selectedDateTimeInMillis = calendar.timeInMillis
        formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
            Date(selectedDateTimeInMillis)
        )
        todo.dueDate=selectedDateTimeInMillis
        viewModel.updateTodo(todo)
    }, hour, minute, true)
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            // Set the date in the calendar
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
            // Update the combined date-time in millis
            selectedDateTimeInMillis = calendar.timeInMillis
            formattedDate = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(
                Date(selectedDateTimeInMillis)
            )
            todo.dueDate=selectedDateTimeInMillis
            viewModel.updateTodo(todo)
        }, year, month, day
    )



    Scaffold(
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0E5ED))
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ){
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color(0xFF24A19C))
                    }

                    IconButton(
                        onClick = {
                            viewModel.deleteTodo(todo)
                            Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete",
                            tint = Color(0xFF24A19C) ,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp))
                        .background(Color.Red),
                    value = titleValue,
                    onValueChange = {
                        titleValue = it
                        todo.title = it
                        viewModel.updateTodo(todo)
                    },
                    label = {
                        Text(text = "Title", fontSize = 18.sp)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent, // Remove the underline when focused
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    minLines = 10,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp)),
                    value = contentValue,
                    onValueChange = {
                        contentValue = it
                        todo.content = it
                        viewModel.updateTodo(todo)
                    },
                    label = {
                        Text(text = "Content", fontSize = 18.sp)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent, // Remove the underline when focused
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        "Date",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    TextButton(
                        onClick = {
                            datePickerDialog.show()
                        }
                    ) {
                        Text(
                            formattedDate,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF24A19C)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        "Time",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    TextButton(
                        onClick = {
                            timePickerDialog.show()
                        }
                    ) {
                        Text(
                            formattedTime,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF24A19C)
                        )
                    }
                }

            }
        }
    )
}
