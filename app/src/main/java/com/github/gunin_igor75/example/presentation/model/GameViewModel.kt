package com.github.gunin_igor75.example.presentation.model

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.example.R
import com.github.gunin_igor75.example.data.impl.GameRepositoryImpl
import com.github.gunin_igor75.example.domain.entity.GameResult
import com.github.gunin_igor75.example.domain.entity.GameSettings
import com.github.gunin_igor75.example.domain.entity.Level
import com.github.gunin_igor75.example.domain.entity.Question
import com.github.gunin_igor75.example.domain.usecases.GenerateQuestionUseCase
import com.github.gunin_igor75.example.domain.usecases.GetGameSettingsUseCase


class GameViewModel(
    private val level: Level,
    private val application: Application
) :ViewModel() {
    private val gameRepository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepository)

    private val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepository)

    private lateinit var gameSettings: GameSettings

    private var countAnswer = 0

    private var countQuestions = 0

    private var timer: CountDownTimer? = null

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private var _percentCorrectedAnswer = MutableLiveData<Int>()
    val percentCorrectedAnswer: LiveData<Int>
        get() = _percentCorrectedAnswer

    private var _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer

    private var _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private var _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private var _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame() {
        getGameSettings()
        startTimer(gameSettings.gameTimeInSeconds)
        getQuestion()
        updateProgress()
    }

    fun selectAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        getQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercent()
        _percentCorrectedAnswer.value = percent // progressbar
        _progressAnswer.value = String.format( // textview
            application.getString(R.string.correct_answers),
            countAnswer, gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countAnswer >= gameSettings.minCountOfRightAnswers // color textview
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers // color progressbar
    }

    private fun calculatePercent(): Int {
        if (countQuestions == 0) return 0
        return ((countAnswer / countQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        if (number == question.value?.correctedAnswer) {
            countAnswer++
        }
        countQuestions++
    }

    private fun getGameSettings() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun getQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun startTimer(second: Int) {
        timer = object : CountDownTimer(
            second * MILLI_SECOND, MILLI_SECOND
        ) {
            override fun onTick(millSecond: Long) {
                _formattedTime.value = getFormatTime(millSecond)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun getFormatTime(millSecond: Long): String {
        val second = millSecond / MILLI_SECOND
        val minute = second / SECOND_MINUTE
        val remainderSecond = second - minute * SECOND_MINUTE
        return String.format("%02d:%02d", minute, remainderSecond)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countAnswer,
            calculatePercent(),
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLI_SECOND = 1000L
        private const val SECOND_MINUTE = 60
    }
}