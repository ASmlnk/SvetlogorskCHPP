package com.example.svetlogorskchpp.__presentation.home_page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.home_page.EquipmentFilter
import com.example.svetlogorskchpp.__presentation.home_page.view_model.HomePageViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutOryBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutTsnBinding
import com.example.svetlogorskchpp.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : BaseFragment<FragmentHomePageBinding>() {

    private val viewModel: HomePageViewModel by viewModels()

    private var _includeOryBinding: ContentLayoutOryBinding? = null
    private val includeOryBinding get() = _includeOryBinding!!

    private var _includeTsnBinding: ContentLayoutTsnBinding? = null
    private val includeTsnBinding get() = _includeTsnBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomePageBinding {

        return FragmentHomePageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _includeOryBinding = ContentLayoutOryBinding.bind(binding.contentOry.root)
        _includeTsnBinding = ContentLayoutTsnBinding.bind(binding.contentTsn.root)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    binding.swipeRefreshLayout.isRefreshing = it
                }
            }
        }

        binding.apply {

            swipeRefreshLayout.setOnRefreshListener {
                lifecycleScope.launch {
                    viewModel.updateLocaleBase()
                }
            }

            addItemTr.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgearTrEditFragment)
            }

            addItemTsn.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_transformerOwnNeedsEditFragment)
            }

            addItemTg.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_turbogeneratorEditFragment)
            }

            layoutSearchView.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_electricMotorSearchFragment2)
            }
            layoutValve.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_valveFragment2)
            }
            layoutTg.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_blockGeneratorTransformer22)
            }
            layoutTg.setOnLongClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_turbogeneratorListFragment)
                return@setOnLongClickListener true
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
            addItem.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgearVlEditFragment)
            }
        }
        includeOryBinding.apply {
            tvOryTransformer.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgearTrListFragment)
            }
            tvOryVl220.setOnClickListener {
                val action = HomePageFragmentDirections.actionHomePageFragmentToOpenSwitchgearVlFragment(EquipmentFilter.ORY_VL)
                findNavController().navigate(action)
            }
            tvOryOther.setOnClickListener {
                val action = HomePageFragmentDirections.actionHomePageFragmentToOpenSwitchgearVlFragment(EquipmentFilter.ORY_OTHER)
                findNavController().navigate(action)
            }
        }

        includeTsnBinding.apply {
            tvTsn103.setOnClickListener {
                val action = HomePageFragmentDirections.actionHomePageFragmentToTransformerOwnNeedsListFragment(Voltage.KV10_KV3)
                findNavController().navigate(action)
            }
        }
    }
}