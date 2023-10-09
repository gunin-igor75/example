package com.github.gunin_igor75.example.presentation.model

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.gunin_igor75.example.R
import com.github.gunin_igor75.example.data.impl.GameRepositoryImpl
import com.github.gunin_igor75.example.domain.entety.GameResult
import com.github.gunin_igor75.example.domain.entety.GameSettings
import com.github.gunin_igor75.example.domain.entety.Level
import com.github.gunin_igor75.example.domain.entety.Question
import com.github.gunin_igor75.example.domain.usecases.GenerateQuestionUseCase
import com.github.gunin_igor75.example.domain.usecases.GetGameSettingsUseCase


class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val gameRepository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepository)

    private val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepository)

    private val context = application

    private lateinit var level: Level

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

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer(gameSettings.gameTimeInSeconds)
        getQuestion()
    }

    fun selectAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        getQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercent()
        _percentCorrectedAnswer.value = percent
        _progressAnswer.value = String.format(
            context.getString(R.string.correct_answers),
            countAnswer, gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countAnswer >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercent(): Int {
        return ((countAnswer / countQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        if (number == question.value?.correctedAnswer) {
            countAnswer++
        }
        countQuestions++
    }

    private fun getGameSettings(level: Level) {
        this.level = level
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
        val second = millSecond * MILLI_SECOND
        val minute = second / SECOND_MINUTE
        val remainderSecond = second - minute * SECOND_MINUTE
        return String.format("%02d:%02d", minute, remainderSecond)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countAnswer,
            countQuestions,
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