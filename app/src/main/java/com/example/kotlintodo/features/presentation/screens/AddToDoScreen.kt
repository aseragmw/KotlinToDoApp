package com.example.kotlintodo.features.presentation.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kotlintodo.R
import com.example.kotlintodo.features.domain.entities.ToDoEntity
import com.example.kotlintodo.features.presentation.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToDoScreen(viewmodel: TodoViewModel, navController: NavHostController) {
    val context = LocalContext.current
    var titleValue by remember {
        mutableStateOf("")
    }
    var contentValue by remember {
        mutableStateOf("")
    }
    var formattedTime by remember {
        mutableStateOf("No Time Picked")
    }
    var formattedDate by remember {
        mutableStateOf("No Date Picked")
    }


    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var hour = calendar.get(Calendar.HOUR_OF_DAY)
    var minute = calendar.get(Calendar.MINUTE)
    var selectedDateTimeInMillis by remember { mutableStateOf(0L) }

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
    }, hour, minute, true)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            // Set the date in the calendar
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
            // Update the combined date-time in millis
            timePickerDialog.show()
            selectedDateTimeInMillis = calendar.timeInMillis
            formattedDate = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(
                Date(selectedDateTimeInMillis)
            )
            formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                Date(selectedDateTimeInMillis)
            )
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color(0xFF24A19C)
                        )
                    }
                    IconButton(
                        onClick = {
                            if (titleValue.isNotEmpty() && contentValue.isNotEmpty() && selectedDateTimeInMillis != 0L) {
                                viewmodel.insertTodo(
                                    ToDoEntity(
                                        title = titleValue,
                                        content = contentValue,
                                        dueDate = selectedDateTimeInMillis
                                    )
                                )
                                navController.popBackStack()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.baseline_save_24),
                            tint = Color(0xFF24A19C),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp)),
                    value = titleValue,
                    onValueChange = {
                        titleValue = it
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp)),
                    value = contentValue,
                    onValueChange = {
                        contentValue = it
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
                        "Picked Date",
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