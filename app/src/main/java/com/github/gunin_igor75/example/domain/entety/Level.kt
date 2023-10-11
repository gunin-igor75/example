package com.github.gunin_igor75.example.domain.entety

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level: Parcelable {
    EASY, NORMAL, HARD, TEST
}