package com.example.myrecipeplanner.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myrecipeplanner.components.InputField
import com.example.myrecipeplanner.viewmodels.UserViewModel

@Composable
fun RegisterScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("BikeMate", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))


        InputField(
            value = nickname,
            onValueChange = { nickname = it },
            label = "닉네임"
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = password,
            onValueChange = { password = it },
            label = "비밀번호",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "비밀번호 확인하기",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    when {
                        nickname.isBlank() -> errorMessage = "닉네임을 입력해주세요."

                        password.isBlank() -> errorMessage = "비밀번호를 입력해주세요."

                        password != confirmPassword -> errorMessage = "비밀번호가 일치하지 않습니다."

                        userViewModel.registerUser(nickname, password) -> navController.navigate("login")

                        else -> errorMessage = "이미 존재하는 닉네임입니다."
                    }
                }
            ) {
                Text("회원가입")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.height(20.dp)) {
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }
        }

        TextButton(onClick = {
            navController.navigateUp()
        }) {
            Text("로그인하기")
        }
    }
}