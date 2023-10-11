package com.github.gunin_igor75.example.domain.entity

data class Question (
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
){
    val correctedAnswer:Int
        get() = sum - visibleNumber
}
