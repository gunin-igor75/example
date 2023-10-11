package com.github.gunin_igor75.example.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.gunin_igor75.example.databinding.FragmentFinishBinding

class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private lateinit var ivLogoGameOver: ImageView
    private lateinit var tvEnoughAnswers: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvEnoughPercent: TextView
    private lateinit var tvPercent: TextView
    private lateinit var btAgainBegin: Button

    private val args by navArgs<FinishFragmentArgs>()

    private val binding: FragmentFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentFinishBinding is null")


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
        initView()
        clickListener()
        binding.gameResult = args.gameResult
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        ivLogoGameOver = binding.ivLogoGameOver
        tvEnoughAnswers = binding.tvEnoughAnswers
        tvScore = binding.tvScore
        tvEnoughPercent = binding.tvEnoughPercent
        tvPercent = binding.tvPercent
        btAgainBegin = binding.btAgainBegin
    }

    private fun clickListener() {
        btAgainBegin.setOnClickListener { retryGame() }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}