package com.github.gunin_igor75.example.domain.usecases

import com.github.gunin_igor75.example.domain.entety.GameSettings
import com.github.gunin_igor75.example.domain.entety.Level
import com.github.gunin_igor75.example.domain.repository.GameRepository

class GetGameSettingsUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return gameRepository.getGameSettings(level)
    }
}