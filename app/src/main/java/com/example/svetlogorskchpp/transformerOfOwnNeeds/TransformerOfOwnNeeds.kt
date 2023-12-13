package com.example.svetlogorskchpp.transformerOfOwnNeeds

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.svetlogorskchpp.databinding.FragmentTransformerOfOwnNeedsBinding
import com.example.svetlogorskchpp.model.powerLines.OverheadPowerLines
import com.example.svetlogorskchpp.model.transformerNeeds.TsnData
import com.example.svetlogorskchpp.openSwitchgear.OpenSwitchgearViewModel

class TransformerOfOwnNeeds : Fragment() {

    private var _binding: FragmentTransformerOfOwnNeedsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransformerOfOwnNeedsViewModel by viewModels()
    val adapter = TransformerOfOwnNeedsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransformerOfOwnNeedsBinding.inflate(inflater, container, false)

        val display = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(display)
        val h = display.heightPixels
        binding.recycleTsn.layoutParams.height = h //* 80 / 100
        binding.recycleTsn.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}