package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleAddNotesBinding
import com.example.svetlogorskchpp.presentation.shift_schedule.viewModel.ShiftScheduleViewModel
import com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.viewModel.ShiftScheduleAddNotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShiftScheduleAddNotesFragment : Fragment() {

    private var _binding: FragmentShiftScheduleAddNotesBinding? = null
    private val binding
        get() = _binding!!

    private val args: ShiftScheduleAddNotesFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ShiftScheduleAddNotesViewModel.ShiftShiftScheduleAddNotesViewModelFactory

    private val viewModel: ShiftScheduleAddNotesViewModel by viewModels {
        ShiftScheduleAddNotesViewModel.providesFactory(
            assistedFactory = viewModelFactory,
            date = args.navigateAddNoteArgs.date,
            idNote = args.navigateAddNoteArgs.idNoteTag
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentShiftScheduleAddNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvDate.text = viewModel.calendarDate()
            buttonShift1.text = getString(R.string.shift, args.navigateAddNoteArgs.prevNightShift.nameApp)
            buttonShift2.text = getString(R.string.shift,args.navigateAddNoteArgs.dayShift.nameApp)
            buttonShift3.text = getString(R.string.shift,args.navigateAddNoteArgs.nextNightShift.nameApp)
            cbTechnical.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // repTagRepository.insertTag(CalendarNoteTagEntity())

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}