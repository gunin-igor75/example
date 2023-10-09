package com.github.gunin_igor75.example.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.gunin_igor75.example.databinding.FragmentFinishBinding
import com.github.gunin_igor75.example.domain.entety.GameResult

class FinishFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null

    private lateinit var gameResult: GameResult
    private val binding: FragmentFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentFinishBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
        binding.btAgainBegin.setOnClickListener { retryGame() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_GAME_RESULT, GameResult::class.java)?.let {
                gameResult = it
            }
        } else {
            requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
                gameResult = it
            }
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager
            .popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"
        fun newInstance(gameResult: GameResult): FinishFragment {
            return FinishFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}