package com.github.gunin_igor75.example.presentation.binding_adapter

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

interface OnOptionClick{
    fun onOptionClick(number: Int)
}

@BindingAdapter("pbAnswer")
fun bindingPbAnswer(progressBar: ProgressBar, percent: Int) {
    progressBar.setProgress(percent, true)
}

@BindingAdapter("pbAnswerEnoughPercent")
fun bindingPbAnswerEnoughPercent(progressBar: ProgressBar, state: Boolean) {
    val color = getColorByState(progressBar.context, state)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("tvEnoughCount")
fun bindingTvEnoughCount(textView: TextView, state: Boolean) {
    val color = getColorByState(textView.context, state)
    textView.setTextColor(color)
}

@BindingAdapter("numberAsText")
fun bindingNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindingOnOptionClickListener(textView: TextView, onOptionClick: OnOptionClick) {
    val number = textView.text.toString().toInt()
    textView.setOnClickListener {
        onOptionClick.onOptionClick(number)
    }
}

private fun getColorByState(context: Context, state: Boolean): Int {
    val colorId = if (state) android.R.color.holo_green_light else android.R.color.holo_red_light
    return ContextCompat.getColor(context, colorId)
}