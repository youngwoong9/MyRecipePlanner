package com.example.myrecipeplanner.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myrecipeplanner.states.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class UserViewModel : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    // 유저 저장소 (추후에 DB 사용할 것)
    private val users = mutableMapOf<String, String>()

    init {
        loadInitialUsers()
    }

    // 테스트용 회원
    private fun loadInitialUsers() {
        users["test"] = "test"
        users["test2"] = "test2"
    }

    fun registerUser(nickname: String, password: String): Boolean {
        if (!users.containsKey(nickname)) {
            users[nickname] = password
            return true
        }
        return false
    }

    fun loginUser(nickname: String, password: String): Boolean {
        val isLoggedIn = users[nickname] == password
        if (isLoggedIn) {
            _userState.update { it.copy(nickname = nickname, password = password) }
        }
        return isLoggedIn
    }

    fun logoutUser() {
        _userState.update { UserState() }
    }

    // 지정한 날짜에 쇼핑 리스트 추가 (임시 연습용)
    fun addItemToShoppingList(date: LocalDate, item: String) {
        val currentState = _userState.value
        val updatedMap = currentState.shoppingToDoMap

        // If the selected date already exists, update the list, otherwise create a new one
        updatedMap[date]?.add(item) ?: run {
            updatedMap[date] = mutableListOf(item)
        }

        // Update the state with the new map
        _userState.value = currentState.copy(shoppingToDoMap = updatedMap)
    }
}