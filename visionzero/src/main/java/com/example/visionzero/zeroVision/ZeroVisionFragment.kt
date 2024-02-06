package com.example.visionzero.zeroVision

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.visionzero.databinding.FragmentZeroVisionBinding

class ZeroVisionFragment : Fragment() {

    private var _binding: FragmentZeroVisionBinding? = null
    private val binding: FragmentZeroVisionBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentZeroVisionBinding.inflate(inflater, container, false)
        binding.apply {
            materialCardViewZeroVision1.setOnClickListener {
                val action = ZeroVisionFragmentDirections.actionZeroVisionFragmentToZeroVisionDialog1("1")
                findNavController().navigate(action)
            }
            materialCardViewZeroVision2.setOnClickListener {
                val action = ZeroVisionFragmentDirections.actionZeroVisionFragmentToZeroVisionDialog1("2")
                findNavController().navigate(action)
            }
            materialCardViewZeroVision3.setOnClickListener {
                val action = ZeroVisionFragmentDirections.actionZeroVisionFragmentToZeroVisionDialog1("3")
                findNavController().navigate(action)
            }
            materialCardViewZeroVision4.setOnClickListener {
                val action = ZeroVisionFragmentDirections.actionZeroVisionFragmentToZeroVisionDialog1("4")
                findNavController().navigate(action)
            }
            materialCardViewZeroVision5.setOnClickListener {
                val action = ZeroVisionFragmentDirections.actionZeroVisionFragmentToZeroVisionDialog1("5")
                findNavController().navigate(action)
            }
            materialCardViewZeroVision6.setOnClickListener {
                val action = ZeroVisionFragmentDirections.actionZeroVisionFragmentToZeroVisionDialog1("6")
                findNavController().navigate(action)
            }
            materialCardViewZeroVision7.setOnClickListener {
                val action = ZeroVisionFragmentDirections.actionZeroVisionFragmentToZeroVisionDialog1("7")
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}