package com.example.myrecipeplanner.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.myrecipeplanner.viewmodels.UserViewModel
import java.time.LocalDate

@Composable
fun RecipeManagementScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    selectedDate: LocalDate?
) {
    var newItem by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedDate != null) {
                TextField(
                    value = newItem,
                    label = { Text("추가할 음식을 입력하세요") },
                    onValueChange = { newItem = it },
                )

                Button(
                    onClick = {
                        if (newItem.isNotBlank()) {
                            selectedDate.let {
                                userViewModel.addItemToShoppingList(it, newItem)
                                navController.navigateUp() // Go back to the previous screen
                            }
                        } else {
                            // Optionally show an error or warning
                            Toast.makeText(context, "음식을 입력하세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(text = "완료")
                }
            }

            Button(
                onClick = { navController.navigateUp() }
            ) {
                Text(text = "뒤로가기")
            }
        }
    }
}