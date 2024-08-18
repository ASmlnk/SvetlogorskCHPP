package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleAddNotesBinding
import com.example.svetlogorskchpp.presentation.shift_schedule.viewModel.ShiftScheduleViewModel
import com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.viewModel.ShiftScheduleAddNotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
            date = args.navigateAddNoteArgs.date
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentShiftScheduleAddNotesBinding.inflate(inflater, container, false)

        binding.apply {
            tvDate.text = viewModel.calendarDate()
            buttonShift1.text =
                getString(R.string.shift, args.navigateAddNoteArgs.prevNightShift.nameApp)
            buttonShift2.text = getString(R.string.shift, args.navigateAddNoteArgs.dayShift.nameApp)
            buttonShift3.text =
                getString(R.string.shift, args.navigateAddNoteArgs.nextNightShift.nameApp)
            cbTechnical.isChecked = args.navigateAddNoteArgs.isTechnical

            cbTechnical.setOnCheckedChangeListener { _, isChecked ->
                viewModel.insertIsTechnical(isChecked)
            }
            ivSaveNotes.setOnClickListener {
                viewModel.insertNote(content = etNotesText.text.toString(), isTimeNote = false)
                etNotesText
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.calendarNoteStream.collect {
                    binding.tvTest.text = it.toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.deleteNoteTag()
        _binding = null
    }


}