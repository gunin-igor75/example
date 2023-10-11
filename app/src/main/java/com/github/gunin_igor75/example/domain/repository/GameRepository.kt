package com.github.gunin_igor75.example.domain.repository

import com.github.gunin_igor75.example.domain.entity.GameSettings
import com.github.gunin_igor75.example.domain.entity.Level
import com.github.gunin_igor75.example.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}