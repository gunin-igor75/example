package com.github.gunin_igor75.example.domain.entety

data class GameResult (
    val winner: Boolean,
    val countRightOfAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)
