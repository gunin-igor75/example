package com.github.gunin_igor75.example.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.gunin_igor75.example.databinding.FragmentGameBinding
import com.github.gunin_igor75.example.domain.entity.GameResult
import com.github.gunin_igor75.example.presentation.model.GameViewModel
import com.github.gunin_igor75.example.presentation.model.GameViewModelFactory

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null

    private val args by navArgs<GameFragmentArgs>()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner) { gameResult ->
            launchFinishFragment(gameResult)
        }
    }

    private fun launchFinishFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToFinishFragment(gameResult)
        )
    }
}