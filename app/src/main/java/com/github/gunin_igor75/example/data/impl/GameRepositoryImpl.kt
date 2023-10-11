package com.github.gunin_igor75.example.data.impl

import com.github.gunin_igor75.example.domain.entity.GameSettings
import com.github.gunin_igor75.example.domain.entity.Level
import com.github.gunin_igor75.example.domain.entity.Level.*
import com.github.gunin_igor75.example.domain.entity.Question
import com.github.gunin_igor75.example.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

private const val MIN_SUM_VALUE = 2

private const val MIN_VISIBLE_NUMBER = 1

object GameRepositoryImpl : GameRepository {

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER, sum)
        val options = hashSetOf<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_VISIBLE_NUMBER)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            val randomNumber = Random.nextInt(from, to)
            options.add(randomNumber)
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            TEST -> {
                GameSettings(
                    10,
                    3,
                    50,
                    20
                )
            }
            EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    70
                )
            }
            NORMAL -> {
                GameSettings(
                    20,
                    15,
                    80,
                    40
                )
            }
            HARD -> {
                GameSettings(
                    30,
                    17,
                    90,
                    40
                )
            }
        }
    }
}