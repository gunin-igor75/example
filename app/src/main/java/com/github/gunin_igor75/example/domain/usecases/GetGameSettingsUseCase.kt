package com.github.gunin_igor75.example.domain.usecases

import com.github.gunin_igor75.example.domain.entity.GameSettings
import com.github.gunin_igor75.example.domain.entity.Level
import com.github.gunin_igor75.example.domain.repository.GameRepository

class GetGameSettingsUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return gameRepository.getGameSettings(level)
    }
}