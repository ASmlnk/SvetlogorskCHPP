package com.example.svetlogorskchpp.__presentation.dialog.request_work_sorted.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.RequestWorkSorted
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.request_work_sorted.view_model.RequestWorkSortedViewModel
import com.example.svetlogorskchpp.databinding.DialogRequestWorkSortedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RequestWorkSortedDialog : BaseBottomSheetDialog<DialogRequestWorkSortedBinding>() {

    private val viewModel: RequestWorkSortedViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogRequestWorkSortedBinding {
        return DialogRequestWorkSortedBinding.bind(
            inflater.inflate(
                R.layout.dialog_request_work_sorted,
                container
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvSortedNumber.setOnClickListener {
                viewModel.setSortedFlag(RequestWorkSorted.NUMBER)
            }
            tvSortedDateOpen.setOnClickListener {
                viewModel.setSortedFlag(RequestWorkSorted.DATE_OPEN)
            }
            tvSortedDateClose.setOnClickListener {
                viewModel.setSortedFlag(RequestWorkSorted.DATE_CLOSE)
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sortedFlagState.collect { sortedFlag ->
                    sortedFlag?.let { setupUI(it) }

                }
            }
        }
    }

    private fun setupUI(sortedFlag: RequestWorkSorted) {
        binding.apply {
            when (sortedFlag) {
                RequestWorkSorted.NUMBER -> {
                    ivSelectDateNumber.isGone = false
                    ivSelectDateOpen.isGone = true
                    ivSelectDateClose.isGone = true
                }

                RequestWorkSorted.DATE_OPEN -> {
                    ivSelectDateNumber.isGone = true
                    ivSelectDateOpen.isGone = false
                    ivSelectDateClose.isGone = true
                }

                RequestWorkSorted.DATE_CLOSE -> {
                    ivSelectDateNumber.isGone = true
                    ivSelectDateOpen.isGone = true
                    ivSelectDateClose.isGone = false
                }
            }
        }
    }

}