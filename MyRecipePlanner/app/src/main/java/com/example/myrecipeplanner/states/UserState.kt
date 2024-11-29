package com.example.myrecipeplanner.states

import java.time.LocalDate

data class UserState(
    var nickname: String = "",
    var password: String ="",

    // 연습용 임시 쇼핑 to-do 맵
    // 날짜와 음식 리스트를 엮어서 임시 to do 리스트 만듦
    // 추후에 수정해야 함
    var shoppingToDoMap: MutableMap<LocalDate, MutableList<RecipeState>> = mutableMapOf()
)