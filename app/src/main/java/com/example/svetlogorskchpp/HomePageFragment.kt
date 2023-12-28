package com.example.svetlogorskchpp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.databinding.FragmentHomePageSmallBinding

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageSmallBinding? = null
    val binding: FragmentHomePageSmallBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageSmallBinding.inflate(inflater, container,false)

        binding.apply {
            materialCardViewOry.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgear)
            }
            materialCardViewTsn.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_transformerOfOwnNeeds2)
            }
            materialCardViewBlockGT.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_blockGeneratorTransformer2)
            }
            materialCardViewZeroVision.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_zeroVisionFragment)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}