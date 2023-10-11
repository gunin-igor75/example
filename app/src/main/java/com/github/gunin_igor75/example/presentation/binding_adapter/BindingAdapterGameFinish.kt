package com.github.gunin_igor75.example.presentation.binding_adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.gunin_igor75.example.R
import com.github.gunin_igor75.example.domain.entity.GameResult

@BindingAdapter("ivLogoGameOver")
fun bindingIvLogoGameOver(imageView: ImageView, gameResult: GameResult) {
    val image = getImageByState(gameResult.winner)
    imageView.setImageResource(image)
}

@BindingAdapter("tvEnoughAnswers")
fun bindingEnoughAnswers(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.tl_corrected_answers),
        gameResult.gameSettings.minCountOfRightAnswers
    )
}

@BindingAdapter("tvScore")
fun bindingTvScore(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score),
        gameResult.countRightOfAnswers
    )
}

@BindingAdapter("tvEnoughPercent")
fun bindingTvEnoughPercent(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.min_percent),
        gameResult.gameSettings.minPercentOfRightAnswers
    )
}

@BindingAdapter("tvPercent")
fun bindingTvPercent(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.percent_corrected_answers),
        gameResult.countOfRightPercent
    )
}

private fun getImageByState(winner: Boolean): Int {
    return if (winner) R.drawable.victory else R.drawable.loss
}


