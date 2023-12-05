package com.example.svetlogorskchpp.blockGeneratorTransformer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentBlockGeneratorTransformerBinding
import com.example.svetlogorskchpp.model.block.BlockData

class BlockGeneratorTransformer : Fragment() {

    private var _binding: FragmentBlockGeneratorTransformerBinding? = null
    private val binding get() = _binding!!
    private val data = BlockData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlockGeneratorTransformerBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = BlockGeneratorTransformerAdapter()
        binding.recycleBlock.adapter = adapter
        val list = data.getList()
        adapter.submitList(list)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}