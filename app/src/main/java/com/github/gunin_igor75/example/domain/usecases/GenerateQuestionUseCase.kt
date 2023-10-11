package com.github.gunin_igor75.example.domain.usecases

import com.github.gunin_igor75.example.domain.entity.Question
import com.github.gunin_igor75.example.domain.repository.GameRepository

class GenerateQuestionUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
       return gameRepository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}