package com.example.svetlogorskchpp.transformerOfOwnNeeds

import android.os.Bundle
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

    private val viewModel: OpenSwitchgearViewModel by viewModels()

    private val data = TsnData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransformerOfOwnNeedsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransformerOfOwnNeedsAdapter()
        binding.recycleTsn.adapter = adapter
        adapter.submitList(data.getList())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}