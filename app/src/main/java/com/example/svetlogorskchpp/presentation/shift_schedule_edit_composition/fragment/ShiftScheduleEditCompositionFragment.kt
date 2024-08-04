package com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleEditCompositionBinding
import com.example.svetlogorskchpp.domain.en.JobTitle
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.adapter.StaffListAdapter
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.Staff
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.viewModel.ShiftScheduleEditCompositionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShiftScheduleEditCompositionFragment: Fragment(){

    private val viewModel: ShiftScheduleEditCompositionViewModel by viewModels()

    private var _binding: FragmentShiftScheduleEditCompositionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShiftScheduleEditCompositionBinding.inflate(inflater, container, false)
        binding.apply {
            autoCompleteNss.isGone = true
            saveNss.isGone = true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val suggestions = listOf(
            Staff (Shift.NO_SHIFT, "Фоменок Вечеслав Николаевич"),
            Staff (Shift.NO_SHIFT, "Лацко Игорь Николаевич"),
            Staff (Shift.NO_SHIFT, "Самсонов Александр Валерьевич"),
            Staff (Shift.NO_SHIFT, "Вариант 4")
        )

        // Создайте кастомный адаптер
        val adapter = StaffListAdapter(requireContext(), suggestions)

        // Установите адаптер для AutoCompleteTextView



        binding.apply {

            autoCompleteNss.setAdapter(adapter)
            autoCompleteNss.setOnItemClickListener { _, _, position, _ ->
                val selectedName = adapter.getItem(position) as Staff
                autoCompleteNss.setText(selectedName.name)
            }
            shiftANssEdit.setOnClickListener {
                autoCompleteNss.isGone = false
                saveNss.isGone = false
                autoCompleteNss.setText(shiftANssTextView.text)
            }
            saveNss.setOnClickListener {
                shiftANssTextView.text = autoCompleteNss.text
                viewModel.save(JobTitle.NSS, Shift.A_SHIFT , shiftANssTextView.text.toString())

                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                autoCompleteNss.isGone = true
                saveNss.isGone = true
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shiftPersonalFlow.collect { list ->
                    binding.apply {

                        if (list.isNotEmpty()) {
                            val listShiftA = list.filter { it.shift == Shift.A_SHIFT }
                            shiftANssTextView.text = listShiftA.first { it.jobTitle == JobTitle.NSS }.namePersonal
                        }

                        //shiftANssTextView.text =
                    }
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            viewModel.saveBd()
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}