package com.github.gunin_igor75.example.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.gunin_igor75.example.databinding.FragmentLevelSelectionBinding
import com.github.gunin_igor75.example.domain.entity.Level
import com.github.gunin_igor75.example.domain.entity.Level.EASY
import com.github.gunin_igor75.example.domain.entity.Level.HARD
import com.github.gunin_igor75.example.domain.entity.Level.NORMAL
import com.github.gunin_igor75.example.domain.entity.Level.TEST

class LevelSelectionFragment : Fragment() {

    private var _binding: FragmentLevelSelectionBinding? = null

    private val binding: FragmentLevelSelectionBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelSelectionBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickChoseLevel()
    }

    private fun onClickChoseLevel() {
        with (binding) {
            btTest.setOnClickListener {
                launchGameFragment(TEST)
            }
            btEasy.setOnClickListener {
                launchGameFragment(EASY)
            }

            btNormal.setOnClickListener {
                launchGameFragment(NORMAL)
            }

            btHard.setOnClickListener {
                launchGameFragment(HARD)
            }
        }
    }
    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            LevelSelectionFragmentDirections.actionLevelSelectionFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}