package com.github.gunin_igor75.example.domain.repository

import com.github.gunin_igor75.example.domain.entety.GameSettings
import com.github.gunin_igor75.example.domain.entety.Level
import com.github.gunin_igor75.example.domain.entety.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}