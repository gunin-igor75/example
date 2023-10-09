package com.github.gunin_igor75.example.domain.entety

data class GameSettings (
    val maxSumValue:Int,
    val minCountOfRightAnswers:Int,
    val monPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)
