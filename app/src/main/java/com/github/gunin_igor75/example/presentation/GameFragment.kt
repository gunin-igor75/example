package com.github.gunin_igor75.example.presentation

import android.R.color
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.gunin_igor75.example.databinding.FragmentGameBinding
import com.github.gunin_igor75.example.domain.entety.GameResult
import com.github.gunin_igor75.example.presentation.model.GameViewModel
import com.github.gunin_igor75.example.presentation.model.GameViewModelFactory

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private lateinit var tvTimer: TextView
    private lateinit var tvNumberLeft: TextView
    private lateinit var tvSum: TextView
    private lateinit var tvNumberQuestion: TextView
    private lateinit var tvProgressAnswer: TextView
    private lateinit var pbAnswer: ProgressBar

    private val args by navArgs<GameFragmentArgs>()

    private val tvOptions: List<TextView> by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private val viewModelFactory by lazy {
        GameViewModelFactory(
            args.level,
            requireActivity().application
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[GameViewModel::class.java]
    }
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.startGame()
        observeViewModel()
        clickListenerViewModel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        tvTimer = binding.tvTimer
        tvNumberLeft = binding.tvNumberLeft
        tvSum = binding.tvSum
        tvNumberQuestion = binding.tvNumberQuestion
        tvProgressAnswer = binding.tvProgressAnswer
        pbAnswer = binding.pbAnswer
    }

    private fun observeViewModel() {
        observeQuestion()
        observeTimer()
        observeIndication()
    }

    private fun observeIndication() {
        viewModel.progressAnswer.observe(viewLifecycleOwner) { info ->
            tvProgressAnswer.text = info
        }
        viewModel.minPercent.observe(viewLifecycleOwner) { minPercent ->
            pbAnswer.secondaryProgress = minPercent
        }
        viewModel.percentCorrectedAnswer.observe(viewLifecycleOwner) { percent ->
            pbAnswer.setProgress(percent, true)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner) { state ->
            val color = getColorByState(state)
            tvProgressAnswer.setTextColor(color)
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner) { state ->
            val color = getColorByState(state)
            pbAnswer.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.gameResult.observe(viewLifecycleOwner) { gameResult ->
            launchFinishFragment(gameResult)
        }
    }

    private fun observeTimer() {
        viewModel.formattedTime.observe(viewLifecycleOwner) { time ->
            tvTimer.text = time
        }
    }

    private fun observeQuestion() {
        viewModel.question.observe(viewLifecycleOwner) { question ->
            tvSum.text = question.sum.toString()
            tvNumberLeft.text = question.visibleNumber.toString()
            for (i in tvOptions.indices) {
                tvOptions[i].text = question.options[i].toString()
            }
        }
    }

    private fun clickListenerViewModel() {
        tvOptions.forEach { tvOption ->
            tvOption.setOnClickListener {
                val number = tvOption.text.toString().toInt()
                viewModel.selectAnswer(number)
            }
        }
    }

    private fun launchFinishFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToFinishFragment(gameResult)
        )
    }

    private fun getColorByState(state: Boolean): Int {
        val colorId = if (state) color.holo_green_light else color.holo_red_light
        return ContextCompat.getColor(requireContext(), colorId)
    }
}