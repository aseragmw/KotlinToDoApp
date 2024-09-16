package com.example.kotlintodo.features.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.SwipeToDismissBox
import com.example.kotlintodo.R
import com.example.kotlintodo.features.domain.entities.ToDoEntity
import com.example.kotlintodo.features.presentation.TodoViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ToDoItem(todo: ToDoEntity, viewModel: TodoViewModel, navController: NavHostController) {
    val color = Color.White
    val context = LocalContext.current

    SwipeToDismissBox(
        modifier = Modifier.fillMaxWidth(),
        backgroundScrimColor =Color.Transparent ,
        contentScrimColor = Color.Transparent,
        onDismissed = {
            viewModel.deleteTodo(todo)
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
        },
    ) {isBackground->
        if (isBackground) {
            Box (modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)){  }
        }
        else{
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth(1f)
                    .background(color)
                    .fillMaxHeight(0.3f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFF24A19C))
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Row {
//                        Icon(imageVector = Icons.Default.Done, contentDescription = null, tint = Color.White)
//                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = todo.title,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    IconButton(
                        onClick = {
                            val jsonItem = Gson().toJson(todo)
                            navController.navigate("details/${jsonItem}")
                        }
                    ) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = todo.content,
                        color = Color.Black,
                        fontSize = 18.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = SimpleDateFormat(
                                "HH:mm",
                                Locale.getDefault()
                            ).format(Date(todo.dueDate)),
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = SimpleDateFormat(
                                "dd/MM/YYYY",
                                Locale.getDefault()
                            ).format(Date(todo.dueDate)),
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

    }

}