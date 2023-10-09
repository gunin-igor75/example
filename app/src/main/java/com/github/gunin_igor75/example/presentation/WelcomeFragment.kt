package com.github.gunin_igor75.example.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.gunin_igor75.example.R
import com.github.gunin_igor75.example.databinding.FragmentWelcomeBinding
import java.lang.RuntimeException

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null

    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBegin.setOnClickListener{
            launchLevelSelectionFragment()
        }
    }

    private fun launchLevelSelectionFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, LevelSelectionFragment.newInstance())
            .addToBackStack(LevelSelectionFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}