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
import com.example.svetlogorskchpp.__domain.en.HardData
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.home_page.EquipmentFilter
import com.example.svetlogorskchpp.__presentation.home_page.adapter.ElMotorChapterAdapter
import com.example.svetlogorskchpp.__presentation.home_page.view_model.HomePageViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutOryBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutSwitchgearBinding
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

    private var _includeSwitchgearBinding: ContentLayoutSwitchgearBinding? = null
    private val includeSwitchgearBinding get() = _includeSwitchgearBinding!!

    private val adapterElMotor = ElMotorChapterAdapter {
        val action = HomePageFragmentDirections.actionHomePageFragmentToElMotorChapterFragment(it)
        findNavController().navigate(action)
    }

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
        _includeSwitchgearBinding =
            ContentLayoutSwitchgearBinding.bind(binding.contentSwitchgear.root)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    binding.swipeRefreshLayout.isRefreshing = it
                }
            }
        }

        binding.apply {

            tvSwitchgear.setOnClickListener {
                //findNavController().navigate(R.id.action_homePageFragment_to_switchgearOwnNeedsListFragment)
            }

            swipeRefreshLayout.setOnRefreshListener {
                lifecycleScope.launch {
                    viewModel.updateLocaleBase()
                }
            }

            addItemTr.setOnClickListener {
                //  findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgearTrEditFragment)
            }

            addItemTsn.setOnClickListener {
                //  findNavController().navigate(R.id.action_homePageFragment_to_transformerOwnNeedsEditFragment)
            }

            addItemTg.setOnClickListener {
                // findNavController().navigate(R.id.action_homePageFragment_to_turbogeneratorEditFragment)
            }

            layoutSearchView.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_searchElectricalFragment)

            }

            layoutSearchView.setOnLongClickListener {
                //findNavController().navigate(R.id.action_homePageFragment_to_electricMotorSearchFragment2)
                return@setOnLongClickListener true
            }

            layoutValve.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_valveFragment2)
            }
            layoutTg.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_turbogeneratorListFragment)
            }
            layoutTg.setOnLongClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_blockGeneratorTransformer22)
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
                // findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgearVlEditFragment)
            }
            ivOryInfo.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToInfoDialog(HardData.INFO_ORY)
                findNavController().navigate(action)
            }
            ivTsnInfo.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToInfoDialog(HardData.INFO_TSN)
                findNavController().navigate(action)
            }
            ivSwitchgearInfo.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToInfoDialog(HardData.INFO_SWITCHGEAR)
                findNavController().navigate(action)
            }
            ivElMotorInfo.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToInfoDialog(HardData.INFO_EL_MOTOR)
                findNavController().navigate(action)
            }
            download.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_loaderBdDialog)

            }
            rvElMotor.adapter = adapterElMotor
            adapterElMotor.submitList(viewModel.elMotorChapter())

        }

        includeOryBinding.apply {
            tvOryTransformer.setOnClickListener {
                findNavController().navigate(R.id.action_homePageFragment_to_openSwitchgearTrListFragment)
            }
            tvOryVl220.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToOpenSwitchgearVlFragment(
                        EquipmentFilter.ORY_VL
                    )
                findNavController().navigate(action)
            }
            tvOryOther.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToOpenSwitchgearVlFragment(
                        EquipmentFilter.ORY_OTHER
                    )
                findNavController().navigate(action)
            }
        }

        includeTsnBinding.apply {
            tvTsn103.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToTransformerOwnNeedsListFragment(
                        Voltage.KV10_KV3
                    )
                findNavController().navigate(action)
            }
            tvTsn106.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToTransformerOwnNeedsListFragment(
                        Voltage.KV10_KV6
                    )
                findNavController().navigate(action)
            }
            tvTsn304.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToTransformerOwnNeedsListFragment(
                        Voltage.KV3_KV04
                    )
                findNavController().navigate(action)
            }
            tvTsn604.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToTransformerOwnNeedsListFragment(
                        Voltage.KV6_KV04
                    )
                findNavController().navigate(action)
            }
        }

        includeSwitchgearBinding.apply {
            tvKry.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.KRY
                    )
                findNavController().navigate(action)
            }
            tvRy.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.RY
                    )
                findNavController().navigate(action)
            }
            tvKtcTo.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.KTC_TO
                    )
                findNavController().navigate(action)
            }
            tvKtcKo.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.KTC_KO
                    )
                findNavController().navigate(action)
            }
            tvKtcTy.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.KTC_TY
                    )
                findNavController().navigate(action)
            }
            tvHc.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.HC
                    )
                findNavController().navigate(action)
            }
            tvIvShieldBlock.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.SHIELD_BLOCK
                    )
                findNavController().navigate(action)
            }
            tvBns.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.BNS
                    )
                findNavController().navigate(action)
            }
            tvCoolingTower.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.COOLING_TOWER
                    )
                findNavController().navigate(action)
            }
            tvPostTok.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.POST_TOK
                    )
                findNavController().navigate(action)
            }
            tvOther.setOnClickListener {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToSwitchgearOwnNeedsListFragment(
                        NameDepartment.OTHER
                    )
                findNavController().navigate(action)
            }
        }
    }
}