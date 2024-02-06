package com.example.svetlogorskchpp.inspectionSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.DialogInspectionScheduleCalendarBinding
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection
import com.example.svetlogorskchpp.model.inspectionSchedule.InspectionChecklist
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class DialogCalendarDateFragment : BottomSheetDialogFragment() {

    private var _binding: DialogInspectionScheduleCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModelFactory: DialogCalendarDateViewModelFactory
    private lateinit var viewModel: DialogCalendarDateViewModel

    override fun getTheme() = R.style.AppBottomSheetDialogTheme
    private val adapter = InspectionAdapter()

    //  private val listInspection = mutableListOf<Inspection>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogInspectionScheduleCalendarBinding.bind(
            inflater.inflate(
                R.layout.dialog_inspection_schedule_calendar,
                container
            )
        )

        val date = DialogCalendarDateFragmentArgs.fromBundle(requireArguments()).date
        val workingShift =
            DialogCalendarDateFragmentArgs.fromBundle(requireArguments()).workingShift

        viewModelFactory = DialogCalendarDateViewModelFactory(date, workingShift)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[DialogCalendarDateViewModel::class.java]

        val dateFormatDay = SimpleDateFormat("dd MMMM yyyy").format(viewModel.getCalendar(InSc.DAY.get,date).time)
        val dateFormatNight = SimpleDateFormat("dd MMMM yyyy").format(viewModel.getCalendar(InSc.NIGHT.get,date).time)
        val dateFormatNextDay = SimpleDateFormat("dd MMMM yyyy").format(viewModel.getNextDate().time)

        binding.apply {
            recycleInspectionSchedule.adapter = adapter
            textNameChecklist.text = dateFormatDay
            textNameChecklistDetail.text = when (workingShift) {
                InSc.DAY.get -> {
                    requireContext().getString(
                        R.string.inspection_schedule_date_day,
                        dateFormatDay
                    )
                }

                InSc.NIGHT.get -> {
                    requireContext().getString(
                        R.string.inspection_schedule_date_night,
                        dateFormatNight, dateFormatNextDay
                    )
                }

                else -> {
                    requireContext().getString(
                        R.string.inspection_schedule_date_day_night,
                        dateFormatDay, dateFormatNight, dateFormatNextDay
                    )
                }

            }

            imageDate.setImageResource(
                when (workingShift) {
                    InSc.DAY.get -> R.drawable.free_icon_sun
                    InSc.NIGHT.get -> R.drawable.free_icon_moon
                    else -> R.drawable.free_icon_day
                }
            )
        }

        lifecycleScope.launch {
            viewModel.inspectionStateFlow.collect {
                adapter.submitList(it)
                // listInspection.addAll(it.toSet().toList())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            chipNse.setOnCheckedChangeListener { _, b ->
                if (b) adapter.submitList(viewModel.inspectionStateFlow.value.filter { it.executor == "НСЭ" })
            }
            chipDem.setOnCheckedChangeListener { _, b ->
                if (b) adapter.submitList(viewModel.inspectionStateFlow.value.filter { it.executor == "ДЭМ" })
            }
            chipAll.setOnCheckedChangeListener { _, b ->
                if (b) adapter.submitList(viewModel.inspectionStateFlow.value)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}