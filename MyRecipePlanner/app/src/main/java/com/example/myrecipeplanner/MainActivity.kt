package com.example.myrecipeplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myrecipeplanner.screens.LoginScreen
import com.example.myrecipeplanner.screens.MainScreen
import com.example.myrecipeplanner.screens.RecipeManagementScreen
import com.example.myrecipeplanner.screens.RegisterScreen
import com.example.myrecipeplanner.ui.theme.MyRecipePlannerTheme
import com.example.myrecipeplanner.viewmodels.UserViewModel
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyRecipePlannerTheme {
                val navController = rememberNavController()

                val userViewModel: UserViewModel = viewModel()

                NavHost(navController = navController, startDestination = "login") {
                    // 로그인 화면
                    composable("login") {
                        LoginScreen(navController, userViewModel)
                    }
                    // 회원가입 화면
                    composable("register") {
                        RegisterScreen(navController, userViewModel)
                    }
                    // 메인(캘린더) 화면
                    composable("main") {
                        MainScreen(navController, userViewModel)
                    }
                    // 레시피 관리 화면
                    composable("recipe") {
                        RecipeManagementScreen(navController, userViewModel, null)
                    }
                    // 레시피 관리 화면 2
                    // LocalDate.parse(it)를 사용하여 수동으로 다시 String에서 LocalDate로 변환
                    composable(
                        "recipe/{selectedDate}",
                    ) { backStackEntry ->
                        val selectedDate = backStackEntry.arguments?.getString("selectedDate")?.let { LocalDate.parse(it) }
                        RecipeManagementScreen(navController, userViewModel, selectedDate)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyRecipePlannerTheme {
    }
}