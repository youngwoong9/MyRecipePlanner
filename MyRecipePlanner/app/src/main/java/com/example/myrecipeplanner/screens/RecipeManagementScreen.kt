package com.example.myrecipeplanner.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myrecipeplanner.states.RecipeState
import com.example.myrecipeplanner.viewmodels.UserViewModel
import java.time.LocalDate

@Composable
fun RecipeManagementScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    selectedDate: LocalDate?
) {
    var recipeName by rememberSaveable { mutableStateOf("") }
    var ingredients by rememberSaveable { mutableStateOf("") }
    var method by rememberSaveable { mutableStateOf("") }

    val loggedInUserState by userViewModel.userStateFlow.collectAsState()

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedDate != null) {
                // 레시피 이름 입력
                OutlinedTextField(
                    value = recipeName,
                    label = { Text("레시피 이름을 입력하세요") },
                    onValueChange = { recipeName = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // 재료 입력
                OutlinedTextField(
                    value = ingredients,
                    label = { Text("재료를 입력하세요 (줄바꿈으로 구분)") },
                    onValueChange = { ingredients = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // 방법 입력
                OutlinedTextField(
                    value = method,
                    label = { Text("조리 방법을 입력하세요 (줄바꿈으로 구분)") },
                    onValueChange = { method = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        // RecipeState 객체를 생성
                        val newRecipe = RecipeState(
                            userNickname = loggedInUserState.nickname,
                            name = recipeName,
                            ingredients = ingredients.split("\n").map { it.trim() },
                            method = method.split("\n").map { it.trim() },
                            isBookMarked = false
                        )

                        if (userViewModel.addItemToShoppingList(selectedDate, newRecipe)) {
                            navController.navigateUp() // 이전 화면으로 돌아가기
                        } else {
                            Toast.makeText(context, "이미 추가된 레시피입니다.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    enabled = recipeName.isNotBlank() && ingredients.isNotBlank() && method.isNotBlank()
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