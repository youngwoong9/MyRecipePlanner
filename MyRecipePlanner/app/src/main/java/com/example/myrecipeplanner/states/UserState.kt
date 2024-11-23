package com.example.myrecipeplanner.states

import java.time.LocalDate

data class UserState(
    var nickname: String = "",
    var password: String ="",

    // 연습용 임시 쇼핑 to-do 맵
    // 날짜와 음식 리스트를 엮어서 임시 to do 리스트 만듦
    // 추후에 수정해야 함
    var shoppingToDoMap: MutableMap<LocalDate, MutableList<String>> =
        mutableMapOf(
            LocalDate.of(2024, 11, 23) to mutableListOf("치킨", "피자"),
            LocalDate.of(2024, 11, 24) to mutableListOf("햄버거", "콜라"),
            LocalDate.of(2024, 11, 25) to mutableListOf("스파게티", "파스타"),
        )
)