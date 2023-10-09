package com.github.gunin_igor75.example.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.gunin_igor75.example.R
import com.github.gunin_igor75.example.databinding.FragmentGameBinding
import com.github.gunin_igor75.example.domain.entety.GameResult
import com.github.gunin_igor75.example.domain.entety.Level

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null

    private lateinit var level: Level
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun launchFinishFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FinishFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_LEVEL, Level::class.java)?.let {
                level = it
            }
        } else {
            requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
                level = it
            }
        }

    }
    companion object {

        private const val KEY_LEVEL = "level"

        const val NAME = "GameFragment"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}