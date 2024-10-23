package com.example.svetlogorskchpp.__presentation.home_page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ContentLayoutOryBinding
import com.example.svetlogorskchpp.databinding.FragmentHomePageBinding

class HomePageFragment : BaseFragment<FragmentHomePageBinding>() {

    private var _includeOryBinding: ContentLayoutOryBinding? = null
    private val includeOryBinding get() = _includeOryBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomePageBinding {

        return FragmentHomePageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _includeOryBinding = ContentLayoutOryBinding.bind(binding.contentOry.root)



        binding.apply {
            layoutSearchView.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_electricMotorSearchFragment2)
            }
            layoutValve.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_valveFragment2)
            }
            layoutTg.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_blockGeneratorTransformer22)
            }
            layoutZeroVision.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_zeroVisionFragment2)
            }
            tvOry.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgear)
            }
            tvTsn.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_transformerOfOwnNeeds2)
            }
        }
        includeOryBinding.apply {
            tvOryVl110.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgearVlEditFragment)
            }
        }
    }
}