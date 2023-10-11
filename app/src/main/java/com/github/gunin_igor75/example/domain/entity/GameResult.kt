package com.github.gunin_igor75.example.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GameResult (
    val winner: Boolean,
    val countRightOfAnswers: Int,
    val countOfRightPercent: Int,
    val gameSettings: GameSettings
): Parcelable
