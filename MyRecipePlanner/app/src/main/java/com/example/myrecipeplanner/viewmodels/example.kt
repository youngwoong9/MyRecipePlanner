package com.example.myrecipeplanner.viewmodels

import java.time.LocalDate

fun main() {
    val shoppingDaysMap: MutableMap<LocalDate, MutableList<String>> = mutableMapOf(
        LocalDate.of(2024, 11, 23) to mutableListOf("치킨", "피자"),
        LocalDate.of(2024, 11, 24) to mutableListOf("햄버거", "콜라"),
        LocalDate.of(2024, 11, 25) to mutableListOf("스파게티", "파스타"),
    )

    println(shoppingDaysMap)

    shoppingDaysMap[LocalDate.of(2024, 11, 26)] = mutableListOf("과자")

    println(shoppingDaysMap)

    shoppingDaysMap[LocalDate.of(2024, 11, 26)]?.add("happy")

    println(shoppingDaysMap)
}