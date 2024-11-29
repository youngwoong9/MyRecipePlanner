package com.example.myrecipeplanner.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myrecipeplanner.states.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class UserViewModel : ViewModel() {
    // 현재 로그인한 유저를 추적
    private val _userStateFlow = MutableStateFlow(UserState())
    val userStateFlow = _userStateFlow.asStateFlow()

    // 회원가입한 유저들을 저장해둔 임시 저장소
    private val _userMapStateFlow = MutableStateFlow<Map<String, UserState>>(emptyMap())
    val userMapStateFlow = _userMapStateFlow.asStateFlow()

    init {
        initializeTestUsers()
    }

    // 테스트용 유저를 미리 회원가입시킨다.
    private fun initializeTestUsers() {
        val initialUsers = mapOf(
            "test" to UserState(nickname = "test", password = "test", shoppingToDoMap = mutableMapOf()),
            "test2" to UserState(nickname = "test2", password = "test2", shoppingToDoMap = mutableMapOf())
        )
        _userMapStateFlow.value = initialUsers
    }

    // 회원가입
    fun registerUser(nickname: String, password: String): Boolean {
        if (!_userMapStateFlow.value.containsKey(nickname)) {
            val newUser = UserState(nickname = nickname, password = password, shoppingToDoMap = mutableMapOf())
            _userMapStateFlow.value += (nickname to newUser)
            return true
        }
        return false
    }

    // 로그인
    fun loginUser(nickname: String, password: String): Boolean {
        val user = _userMapStateFlow.value[nickname]
        if (user != null && user.password == password) {
            _userStateFlow.update { user.copy(password = password) }
            return true
        }
        return false
    }

    // 로그아웃
    fun logoutUser() {
        _userStateFlow.update { UserState() }
    }

    // 지정한 날짜에 쇼핑 리스트 추가 (임시 연습용)
    fun addItemToShoppingList(date: LocalDate, item: String) {
        val currentState = _userStateFlow.value
        val updatedMap = currentState.shoppingToDoMap

        val currentList = updatedMap[date] ?: mutableListOf()

        // Only add the item if it doesn't already exist in the list
        if (!currentList.contains(item)) {
            currentList.add(item)
        }

        // Update the map with the new or modified list for the selected date
        updatedMap[date] = currentList

        // Update the state with the new map
        _userStateFlow.value = currentState.copy(shoppingToDoMap = updatedMap)
    }
}